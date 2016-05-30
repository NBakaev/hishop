/*
 * Copyright 2016 the original author or authors.
 */

package ru.nbakaev.hishop.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nikita Bakaev, ya@nbakaev.ru
 *         Date: 5/30/2016
 *         All Rights Reserved
 */
public interface StorageProvider {

    StorageDocumentMeta uploadFile(MultipartFile fileSource);

}
