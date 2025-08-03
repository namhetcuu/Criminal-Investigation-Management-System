/*
 * @ (#) LogoutRequest.java  1.0 7/10/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.dto.request;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/10/2025
 * @version:    1.0
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequest {
    String token;
}
