package com.ssafy.soltravel.dto.participants;


import com.ssafy.soltravel.dto.user.UserProfileResponseDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {

    private Long participantId;

    private UserProfileResponseDto userInfo;

    private boolean isMaster;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
