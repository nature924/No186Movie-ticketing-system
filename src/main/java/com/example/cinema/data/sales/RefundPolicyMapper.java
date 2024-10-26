package com.example.cinema.data.sales;

import com.example.cinema.po.RefundPolicy;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RefundPolicyMapper {
    int insertRefundPolicy(RefundPolicy refundPolicy);

    List<RefundPolicy> getAllPolicy();

    int updateRefundPolicy(RefundPolicy refundPolicy);

    int deleteRefundPolicy(int target_id);

    RefundPolicy selectPolicyById(int target);
}
