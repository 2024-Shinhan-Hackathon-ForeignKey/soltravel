package com.ssafy.soltravel.mapper;

import com.ssafy.soltravel.domain.Participant;
import com.ssafy.soltravel.dto.participants.ParticipantDto;

public class ParticipantMapper {

    public static ParticipantDto toDto(Participant participant) {

        ParticipantDto participantDto = ParticipantDto.builder()
            .participantId(participant.getId())
            .userId(participant.getUser().getUserId())
            .isMaster(participant.isMaster())
            .createdAt(participant.getCreatedAt())
            .updatedAt(participant.getUpdatedAt())
            .build();
        
        return participantDto;
    }
}
