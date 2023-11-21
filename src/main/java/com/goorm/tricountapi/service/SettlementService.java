package com.goorm.tricountapi.service;

import com.goorm.tricountapi.model.Member;
import com.goorm.tricountapi.model.Settlement;
import com.goorm.tricountapi.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepository settlementRepository;

    // create and join settlement
    @Transactional
    public Settlement createAndJoinSettlement(String settlementName, Member member) {
        Settlement settlement = settlementRepository.create(settlementName);
        settlement.setParticipants(Collections.singletonList(member));
        // 중간테이블도 반드시 추가
        settlementRepository.addParticipantToSettlement(settlement.getId(), member.getId());

        return settlement;
    }

    // join settlement
    @Transactional
    public void joinSettlement(Long settlementId, Long memberId) {
        // TODO 없는 아이디를 요청했을 때 예외 처리, 없는 정산 아이디를 요청했을 때 예외 처리
        settlementRepository.addParticipantToSettlement(settlementId, memberId);
    }
}
