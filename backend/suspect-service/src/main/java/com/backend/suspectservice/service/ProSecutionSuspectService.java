/*
 * @ (#) ProSecutionSuspectService.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.service;


/*
 * @description
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
public interface ProSecutionSuspectService {

    /**
     * Xử lý các nghi phạm trong vụ án
     *
     * @param suspectId ID của nghi phạm
     * @return true nếu xử lý thành công, false nếu không thành công
     */
    boolean processSuspect(Long suspectId);

    /**
     * Lấy thông tin chi tiết về nghi phạm
     *
     * @param suspectId ID của nghi phạm
     * @return thông tin chi tiết về nghi phạm
     */
    String getSuspectDetails(Long suspectId);
}
