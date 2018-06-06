/*
 * Document   : Consts
 * Content    : Общие константы
 * Created on : 27.02.2011, 14:51:57
 * Author     : kajam
 * Description: Управляемый бин доступный в JSF
 */


package ru.reso.wp.web.consts;


import javax.inject.Named;
import javax.faces.bean.ApplicationScoped;

/**
 * Общие константы
 *
 * @author kajam
 */
@Named("consts")
@ApplicationScoped
public class Consts {


    /**
     * Folder
     */
    public static final String folderSections = "sections";
    public static final String folderReports = "reports";
    public static final String folderReportPanels = "panels";
    public static final String folderReportResults = "agent_activity_results";
    public static final String folderReportAddition = "addition";

    /**
     * Templates
     */
    public static final String TEMPLATE_PANEL_HEADER = ". %s";
    public static final String TEMPLATE_PANEL_PAGRPPRODUCTS_GROUP_PRODUCT = "%s / %s";
    public static final String TEMPLATE_SQUARE_BRACKET = "[%s]";
    public static final String TEMPLATE_SQUARE_TEXT_BRACKET = "%s [%s]";


    /**
     * Enable Actions
     *
     * Список возможных действий, в результате которых вызываются одноименные
     * страницы сайта. Доступ, к которым настроен в файле faces-config.xml в
     * разделе <navigation-rule>
     */
    public static enum actions {

        login, loginCustomer, changePsw, stop, logout, unknown,
        viewDocument,
        viewFolderList, viewFolder,
        viewReportForm, viewReportList, viewReportTaskList, viewReportTask, deleteReportTask,
        viewNewsList, viewNews,
        viewSpecialOfferList, viewSpecialOffer,
        viewActivityResultList, viewActivityResult,
        viewResoExpressStatisticCalculations,
        viewResoExpressStatisticPolicies,
        viewResoExpressStatisticCalculationsByDay,
        viewResoExpressStatisticCalculationsByMonth,
        viewResoExpressStatisticCalculationsByBrand,
        viewResoExpressStatisticCalculationsByModel,
        viewResoExpressStatisticCalculationsByModelYearPrice,
        viewFeedBack,
        viewMarketingPresentationReso,
        viewMarketingAnalizeRegionMarket,
        viewMarketingPresentationForClients,
        viewMarketingMaterialsForAgents,
        viewMarketingCompareCascoCond,
        viewMarketingCompareHouseCond,
        viewOrders,
        viewCalcCasco,
        viewResoSchoolEmployees,
        viewResoSchoolSPBEmployees,
        viewResoSchoolManagerInsideTrainer,
        viewResoSchoolManagerStandart,
        viewResoSchoolProducts,
        viewOnlineShop,
        viewKBM,
        insertOrderData,
        annulOrderData,
        viewOnlineShopOrders,
        incOrder,
        decOrder,
        addOrder,
        delOrder,
        reCalc,
        viewROSDelivery,
        viewPolicy,
        viewPolicyList,
        viewGreenCardPolicy,
        viewGreenCardPolicyList,
        viewPolicyInvestLife,
        viewPolicyInvestLifeList,
        viewPolicyCustomer,
        viewPolicyListCustomer,
        viewUndefinedCustomer,
        viewClient
    };

}
