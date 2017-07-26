package poi;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 描述
 * Created by liubingguang on 2017/7/25.
 */
@Data
public class PartnerShipVO {

    private String bigArea;
    private String area;
    private String store;
    private String group;

    /**
     * 业绩
     */
    private BigDecimal actualBelongAchievement;
    private BigDecimal newHouseAchievement;
    private BigDecimal secondHandHouseAchievement;
    private BigDecimal rentAchievement;
    private BigDecimal houseManageAchievement;
    private BigDecimal otherAchievement;

    /**
     * 提佣成本
     */
    private BigDecimal commissionCost;
    private BigDecimal M1ManagerCommission;
    private BigDecimal M2ManagerCommission;
    private BigDecimal D1ManagerCommission;
    private BigDecimal D2ManagerCommission;
    private BigDecimal corporationCommission;
    private BigDecimal agentCommission;
    private BigDecimal houseManagerCommission;

    /**
     * 其它变动成本
     */
    private BigDecimal otherCost;
    private BigDecimal businessTax;

    /**
     * 门店运营成本
     */
    private BigDecimal storeOperationCost;
    private BigDecimal fixedOperationTotalCost;
    private BigDecimal decorationCost;
    private BigDecimal rentCost;
    private BigDecimal officeCost;
    private BigDecimal operationSupportCost;
    private BigDecimal cleaningCost;
    private BigDecimal greenRentCost;
}
