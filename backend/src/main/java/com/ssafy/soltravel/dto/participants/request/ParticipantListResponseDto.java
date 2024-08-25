package com.ssafy.soltravel.dto.participants.request;

import com.ssafy.soltravel.dto.participants.ParticipantDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantListResponseDto {

    private Long accountId;

    List<ParticipantDto> participants;

}
