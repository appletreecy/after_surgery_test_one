// src/main/java/com/example/welcome/repository/AfterSurgeryJoinRepositoryImpl.java
package com.example.welcome.repository;

import com.example.welcome.dto.AfterSurgeryJoinDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AfterSurgeryJoinRepositoryImpl implements AfterSurgeryJoinRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AfterSurgeryJoinDto> fetchJoinedData(LocalDate start, LocalDate end, Pageable pageable) {
        // Only allow sorting by date to keep SQL safe and stable
        String orderDir = "DESC";
        var dateOrder = pageable.getSort().getOrderFor("date");
        if (dateOrder != null && dateOrder.isAscending()) {
            orderDir = "ASC";
        }

        String selectSql =
                "SELECT t1.date, " +
                        " t1.numOfPostoperativeVisits, " +
                        " t1.numOfPostoperativeAnalgesiaCases, " +
                        " t1.numOfAdverseReactionCases, " +
                        " t1.numOfInadequateAnalgesia, " +
                        " t2.numOfNauseaAndVomiting, " +
                        " t2.numOfDizziness, " +
                        " t2.numOfNauseaAndVomitingAndDizziness, " +
                        " t2.numOfItching, " +
                        " t2.numOfAllergicRash, " +
                        " t2.numOfProlongedAnestheticRecovery, " +
                        " t2.numOfPunctureSiteAbnormality, " +
                        " t2.numOfAbdominalDistension, " +
                        " t2.numOfEndotrachealIntubationDiscomfort, " +
                        " t2.numOfEpigastricPain, " +
                        " t2.numOfDelirium, " +
                        " t2.numOfChestDiscomfort, " +
                        " t2.numOfTourniquetReaction, " +
                        " t2.numOfOther, " +
                        " t2.otherComments, " +
                        " t3.numOfJointComplicationCount, " +
                        " t3.numOfMotorDysfunctionCount, " +
                        " t3.numOfTraumaComplicationCount, " +
                        " t3.numOfAnkleComplicationCount, " +
                        " t3.numOfPediatricAdverseEventCount, " +
                        " t3.numOfSpinalComplicationCount, " +
                        " t3.numOfHandSurgeryComplicationCount, " +
                        " t3.numOfObstetricAdverseEventCount, " +
                        " t3.numOfGynecologicalAdverseEventCount, " +
                        " t4.numOfFormulationOne, " +
                        " t4.numOfFormulationTwo, " +
                        " t4.numOfFormulationThree, " +
                        " t4.numOfFormulationFour, " +
                        " t4.numOfFormulationFive, " +
                        " t4.numOfFormulationSix " +
                        "FROM afterSurgeryTableOne t1 " +
                        "JOIN afterSurgeryTableTwo t2 ON t1.date = t2.date " +
                        "JOIN afterSurgeryTableThree t3 ON t1.date = t3.date " +
                        "JOIN afterSurgeryTableFour t4 ON t1.date = t4.date " +
                        "WHERE t1.date BETWEEN :start AND :end " +
                        "ORDER BY t1.date " + orderDir;

        Query dataQuery = entityManager.createNativeQuery(selectSql);
        dataQuery.setParameter("start", java.sql.Date.valueOf(start));
        dataQuery.setParameter("end", java.sql.Date.valueOf(end));
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Object[]> rows = dataQuery.getResultList();

        String countSql =
                "SELECT COUNT(*) " +
                        "FROM afterSurgeryTableOne t1 " +
                        "JOIN afterSurgeryTableTwo t2 ON t1.date = t2.date " +
                        "JOIN afterSurgeryTableThree t3 ON t1.date = t3.date " +
                        "JOIN afterSurgeryTableFour t4 ON t1.date = t4.date " +
                        "WHERE t1.date BETWEEN :start AND :end";

        Query countQuery = entityManager.createNativeQuery(countSql);
        countQuery.setParameter("start", java.sql.Date.valueOf(start));
        countQuery.setParameter("end", java.sql.Date.valueOf(end));
        long total = ((Number) countQuery.getSingleResult()).longValue();

        List<AfterSurgeryJoinDto> content = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            int i = 0;
            var dto = new AfterSurgeryJoinDto();
            dto.setDate(row[i] instanceof java.sql.Date ? ((java.sql.Date) row[i]).toLocalDate() : (LocalDate) row[i]); i++;
            dto.setNumOfPostoperativeVisits(((Number) row[i++]).intValue());
            dto.setNumOfPostoperativeAnalgesiaCases(((Number) row[i++]).intValue());
            dto.setNumOfAdverseReactionCases(((Number) row[i++]).intValue());
            dto.setNumOfInadequateAnalgesia(((Number) row[i++]).intValue());
            dto.setNumOfNauseaAndVomiting(((Number) row[i++]).intValue());
            dto.setNumOfDizziness(((Number) row[i++]).intValue());
            dto.setNumOfNauseaAndVomitingAndDizziness(((Number) row[i++]).intValue());
            dto.setNumOfItching(((Number) row[i++]).intValue());
            dto.setNumOfAllergicRash(((Number) row[i++]).intValue());
            dto.setNumOfProlongedAnestheticRecovery(((Number) row[i++]).intValue());
            dto.setNumOfPunctureSiteAbnormality(((Number) row[i++]).intValue());
            dto.setNumOfAbdominalDistension(((Number) row[i++]).intValue());
            dto.setNumOfEndotrachealIntubationDiscomfort(((Number) row[i++]).intValue());
            dto.setNumOfEpigastricPain(((Number) row[i++]).intValue());
            dto.setNumOfDelirium(((Number) row[i++]).intValue());
            dto.setNumOfChestDiscomfort(((Number) row[i++]).intValue());
            dto.setNumOfTourniquetReaction(((Number) row[i++]).intValue());
            dto.setNumOfOther(((Number) row[i++]).intValue());
            dto.setOtherComments((String) row[i++]);
            dto.setNumOfJointComplicationCount(((Number) row[i++]).intValue());
            dto.setNumOfMotorDysfunctionCount(((Number) row[i++]).intValue());
            dto.setNumOfTraumaComplicationCount(((Number) row[i++]).intValue());
            dto.setNumOfAnkleComplicationCount(((Number) row[i++]).intValue());
            dto.setNumOfPediatricAdverseEventCount(((Number) row[i++]).intValue());
            dto.setNumOfSpinalComplicationCount(((Number) row[i++]).intValue());
            dto.setNumOfHandSurgeryComplicationCount(((Number) row[i++]).intValue());
            dto.setNumOfObstetricAdverseEventCount(((Number) row[i++]).intValue());
            dto.setNumOfGynecologicalAdverseEventCount(((Number) row[i++]).intValue());
            dto.setNumOfFormulationOne(((Number) row[i++]).intValue());
            dto.setNumOfFormulationTwo(((Number) row[i++]).intValue());
            dto.setNumOfFormulationThree(((Number) row[i++]).intValue());
            dto.setNumOfFormulationFour(((Number) row[i++]).intValue());
            dto.setNumOfFormulationFive(((Number) row[i++]).intValue());
            dto.setNumOfFormulationSix(((Number) row[i++]).intValue());
            content.add(dto);
        }

        return new PageImpl<>(content, pageable, total);
    }
}
