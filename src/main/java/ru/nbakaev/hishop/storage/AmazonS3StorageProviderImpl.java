/*
 * Copyright 2016 the original author or authors.
 */

package ru.nbakaev.hishop.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nbakaev.hishop.configs.InvalidRequestException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Bakaev, ya@nbakaev.ru
 *         Date: 5/30/2016
 *         All Rights Reserved
 */
@Service
public class AmazonS3StorageProviderImpl implements StorageProvider {

    private final AmazonS3 conn;
    private final static String PUBLIC_BUCKET_URL_PREFIX = "http://hishop.s3-website-us-west-2.amazonaws.com/";
    public final static String DEFAULT_BUCKET_NAME = "hishop";

    @Autowired
    public AmazonS3StorageProviderImpl(@Value("${aws.accessKey}") String accessKey,
                                       @Value("${aws.secretKey}") String secretKey
    ) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.conn = new AmazonS3Client(credentials);
    }

    @Override
    public StorageDocumentMeta uploadFile(MultipartFile fileSource) {

        File file;

        String unicId = new ObjectId().toString();
        String keyName = unicId + "/" + fileSource.getOriginalFilename();
        String existingBucketName = DEFAULT_BUCKET_NAME;

        StorageDocumentMeta storageDocumentMeta = new StorageDocumentMeta();
        storageDocumentMeta.setPublicUrl(PUBLIC_BUCKET_URL_PREFIX + keyName);

        // note: don't use just `file2 = new File(storage.getId());`
        // because you fill have wrong absolute path in file
        // and then will not be able to use this `File`
        try {
            file = File.createTempFile(unicId, ".data");
            fileSource.transferTo(file);

        } catch (IOException e) {
            throw new InvalidRequestException(e.getMessage());
        }

        List<PartETag> partETags = new ArrayList<PartETag>();

        InitiateMultipartUploadRequest initRequest = new
                InitiateMultipartUploadRequest(existingBucketName, keyName);
        InitiateMultipartUploadResult initResponse =
                conn.initiateMultipartUpload(initRequest);

        long contentLength = file.length();
        long partSize = 5242880; // Set part size to 5 MB.

        try {
            // Step 2: Upload parts.
            long filePosition = 0;
            for (int i = 1; filePosition < contentLength; i++) {
                // Last part can be less than 5 MB. Adjust part size.
                partSize = Math.min(partSize, (contentLength - filePosition));

                // Create request to upload a part.
                UploadPartRequest uploadRequest = new UploadPartRequest()
                        .withBucketName(existingBucketName).withKey(keyName)
                        .withUploadId(initResponse.getUploadId()).withPartNumber(i)
                        .withFileOffset(filePosition)
                        .withFile(file)
                        .withPartSize(partSize);

                // Upload part and add response to our list.
                partETags.add(
                        conn.uploadPart(uploadRequest).getPartETag());

                filePosition += partSize;
            }

            // Step 3: Complete.
            CompleteMultipartUploadRequest compRequest = new
                    CompleteMultipartUploadRequest(
                    existingBucketName,
                    keyName,
                    initResponse.getUploadId(),
                    partETags);

            conn.completeMultipartUpload(compRequest);
        } catch (Exception e) {
            conn.abortMultipartUpload(new AbortMultipartUploadRequest(
                    existingBucketName, keyName, initResponse.getUploadId()));
        }

        if (file != null) {
            file.delete();
        }

        return storageDocumentMeta;
    }
}
