package com.argusoft.kite.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;

/**
 *
 * @author
 */
public class ConstantUtil {

    public static Integer MAX_PAGE_SIZE = 10;
    public static String COUNT = "totalrecords";
    public static String USER_ID_IMPLICIT_PARAMETER = "#loggedInUserId#";
    public static String DESIGNATION_IDS = "designationIds";
    public static String EMPLOYEE_IDS = "employeeIds";
    public static String FEATURE_JSON = "featureJSON";
    public static String EMP_MENU_ITEM_ID = "empMenuItemId";
    public static Integer ROUND;
    public static Float MAX_DISCOUNT;
    public static long SERVER_COMPANY_ID;
    public static String DESC = "desc";
    public static String ASC = "asc";
    public static String BOMBAY_REPAIRING_LOT = "BOMBAY_REPAIRING_LOT";

    public static List<String> groupsForMaxDisc = new ArrayList<String>() {
        {
            add("LAB_DICS");
            add("BASE_PREMIUM");
            add("BASE");
            add("CUT_POL_SYM_DISC");
            add("CUT_POL_SYM_CC");
            add("CUT_Dics");
        }
    };

    public static class EngineerLockType {

        public static final String OTHER_LOCK = "otherlock";
        public static final String REMARKS = "remarksnote";
        public static final String QUALITY_SAME_TICK = "qualitysametick";
    }

    public static class PlanSaveActionType {

        public static final String SAVE = "SAVE";
        public static final String SAVE_FINAL = "SAVE_FINAL";
        public static final String DISPUTE = "DISPUTE";
        public static final String MULTY = "MULTY";
        public static final String ENG_PLAN_CHANGE = "ENG_PLAN_CHANGE";
    }

    public static class StockTallyStatus {

        public static final String TAlLY = "Tally";
        public static final String NOT_TALLY = "Not Tally";
        public static final String FALSE_TALLY = "False Tally";
    }

    public static class TagType {

        public static final String EXTRA = "EX";
        public static final String DIAMOND = "D";
    }

    public static class RequestPathType {

        public static final String REQUEST = "R";
        public static final String ISSUE_RECEIVE = "I";
    }

    public static class SystemConfigurations {

        public static final String FILE_UPLOAD_PATH = "fileuploadpath";
        public static final String CRYSTAL_REPORT_SERVER_URL = "crystalreportserver";
        public static final String REPORT = "Report";
        public static final String LAST_STOCK_TALLY_DATE = "laststocktallydate";
        public static final String ROUGH_PRED_START_TIME = "roughpredstart";
        public static final String ROUGH_PRED_END_TIME = "roughpredend";
        public static final String MAX_PREDICTION = "maxprediction";
        public static final String MIN_PREDICTION = "minprediction";
        public static final String MAX_DISCOUNT = "maxdsc";
        public static final String MAX_EXPECTED_GRAPH_CARAT_DIFFERENCE = "maxexpectedgraphcaratdifference";
        public static final String MUMBAI_SYNC_DATE = "mumbaisyncdate";
//        public static final String STOCK_TALLY_START = "stocktally";
        public static final String MAX_IMAGES_FOR_QC_OPERATOR = "maxImages";
        public static final String GET_IMAGE_TIME_RESTRICTION = "GET_IMAGE_TIME_RESTRICTION";
        public static final String MAX_IMAGES_FOR_QC_CHECKER = "maxchkget";
        public static final String QC_DONE_FILE_LOCATION = "qcdone";
        public static final String CHECKER_DONE_FILE_LOCATION = "chkdone";
        public static final String GALAXY_FILE_LOCATION = "galaxy";
        public static final String QC_READY_FILE_LOCATION = "qcready";
        public static final String HIGH_DIAMOND_VAUE = "HIGH_DIAMOND_VAUE";
        public static final String NO_OF_PACKET_REQ_FOR_NUMBER_STOCK_TALLY = "NO_OF_PACKET_REQ_FOR_NUMBER_STOCK_TALLY";

    }

    public static class MenuTypes {

        public static final String DASHBOARD = "dashboard";
        public static final String RAPCALCY = "rapcalcy";
        public static final String CONFIGURATION = "configuration";
        public static final String REPORT = "report";
        public static final String ISSUERECEIEVE = "issuereceive";
        public static final String DYNAMICVIEWS = "dynamicviews";
        public static final String PACKET = "packet";
        public static final String LOCK = "lock";

    }

    public static class DueDaysType {

        public static final String MINUTE = "M";
        public static final String HOUR = "H";
        public static final String DAY = "D";

    }

    public static class ProcessType {

        public static final String PROCESS = "P";
        public static final String SUB_PROCESS = "SP";
        public static final String SUB_SUB_PROCESS = "SSP";

    }

    public static Map<String, String> implicitParametersMap = new HashMap<String, String>() {
        {
            put(USER_ID_IMPLICIT_PARAMETER, USER_ID_IMPLICIT_PARAMETER);
        }
    };

    public static class CalcParameters {

        public static final String color = "COL";
        public static final String clarity = "CLR";
        public static final String shape = "SHAPE";
        public static final String RDATE = "RDate";
        public static final String RTYPE = "RTYPE";
        public static final String CARAT = "CARAT";
    }

    public static Map<String, String> graphParamMap = new HashMap<String, String>() {
        {
            put("GCUT", "CUT");
            put("GPOL", "POL");
            put("GSYM", "SYM");
            put("GCARAT", "CARAT");
            //TODO: this is for legacy code. remove once code clean up is done
            put("gCarat", "carat");

        }
    };
    public static final List<String> calcParameters = Arrays.asList(
            "LAB", "EC", "BIT", "WIC", "WIT", "MIX_SIZE", "DIA_MM", "OPCR", "CDM", "OPTA",
            //"COL", "CLR", "SHAPE","TBL", "MIX_CLARITY",  "CRTWT","DP",
            "FLOURESENCE", "CERTIFICATION", "PAVILION", "FRANCHISE", "SYM", "POL", "CUT",
            "COL_SHADE", "LUS", "BIC", "MILK", "NATTS", "LBLC", "NATURAL", "GRAIN", "HEARTANDARROW");

    public static final List<String> CUT_DISC_GROUP_NAMES = Arrays.asList("CUT_Dics",
            "CUT_POL_SYM_CC", "CUT_POL_SYM_CC");
    public static final ArrayList<String> GROUP_NAME_EXCEPTIONS_RAP_CALC = new ArrayList<String>() {
        {

            add("LAB_CHARGES");
//            add("BASE");
//            add("SURAT_BACK");
//            add("MIX_ASRT_DISC");
            add("F_C");
            add("CS_B");
            add("P_S");
            add("BI_CC");
            add("TO_CC");
            add("MM_B");
            add("FT_PM");
            add("Base_Single");
            add("DIA_Prem");
            add("CUT_CL_TST");
            add("GIA_INT_CHARGES");
//            add("L_M_B");
            add("WI_CC");
//            add("MIX_BASE");
            add("MIX_PREMIUM");
            add("AGE_Cal");
            add("AGE_Calc");
            add("MFG_DISC_RD");
            add("MFG_DISC_FN");
            add("Pol_Disc");
            add("MKT_MIX_BASE");
            add("BASE_IGI_PREMIUM");
//            add("MIX_ASRT_DISC");
            add("MRKT_MIX");
            add("Test");
            add("MIX_PRM");
            add("Sym_Disc");
            add("TBL_Disc");
            add("MM_BB");
            add("SING_SLBL_BASE");
            add("MIX_SLBL_BASE");
            add("EXT_DISC");
//            add("LAB_DICS");
            add("TEST_MNL_CK");
            add("lab");
//            add("CUT_Dics");

        }
    };

    public static Map<String, Map<Integer, String>> CALC_PARAM_VAL_MAP = new HashMap<>();

    public static class Process {

        public static final int EYE_CAP = 11;
        public static final int GALAXY_DEPT = 12;
        public static final int GALAXY_SARIN = 13;
        public static final int GALAXY_STICKING = 14;
        public static final int GALAXY_MACHINE = 15;
        public static final int GALAXY_PRE_BOIL = 16;
        public static final int GALAXY_POST_BOIL = 17;
        public static final int GALAXY_COMPLETE = 18;
        public static final int ROUGH_STOCK = 19;
        public static final int ORIGINAL = 20;
        public static final int COLLECTION = 21;
        public static final int JOKHAM = 22;
        public static final int DISTRIBUTOR = 23;
        public static final int WINDOW_OPEN = 24;
        public static final int ORIGINAL_RECEIVE = 25;
        public static final int HELIUM = 26;
        public static final int SAWING_DEPT = 27;
        public static final int SAW_LOOKING = 28;
        public static final int SAW_STICKING = 29;
        public static final int SAW_MACHINE = 30;
        public static final int VSHAPE = 31;
        public static final int CROSS_CUTTING = 32;
        public static final int BRUTING = 33;
        public static final int SAW = 34;
        public static final int UN_SAW = 35;
        public static final int SAW_COMPLETE = 36;
        public static final int PEN_SIGN = 37;
        public static final int CHECKER = 38;
        public static final int ENGG_LOOKING = 39;
        public static final int ENGG_FINAL = 40;
        public static final int ROUGH_MAKABLE = 42;
        public static final int MAKABLE_CHECKER = 43;
        public static final int BLOCKING = 44;
        public static final int WINNER = 45;
        public static final int PELCONNING = 46;
        public static final int EXTRA = 47;
        public static final int BOIL = 48;
        public static final int COLOR_CHECK = 49;
        public static final int HISTORY = 50;
        public static final int GALAXY_PENDING = 51;
        public static final int HIGH_AMOUNT = 52;
        public static final int CROSS_CHECK = 53;
        public static final int MKBL_CHECKER_RETURN = 54;
        public static final int FINAL_MAKABLE = 55;
        public static final int MAKABLE_ISSUE = 56;
        public static final int MFGSTOCK_ROOM = 57;
        public static final int MFGMANAGER = 58;
        public static final int ARTIST_LOOKING = 59;
        public static final int CLEVING_REPAIRING = 60;
        public static final int FULTOP = 61;
        public static final int MAXI = 62;
        public static final int MAGNUS = 63;
        public static final int MFG_SARIN = 64;
        public static final int DHAR = 65;
        public static final int MFGBLOCKING = 66;
        public static final int MFGFINAL = 67;
        public static final int POLISH_CHECKER = 68;
        public static final int GRADING_STOCK_ROOM = 69;
        public static final int GRADING_BOIL = 70;
        public static final int GRADING_NATTS = 71;
        public static final int GRADER = 72;
        public static final int BYISSUE = 30116;
        public static final int COMPUTER_OP = 74;
        public static final int MUMBAI = 75;
        public static final int GIA = 78;
        public static final int GALAXY_CO = 79;
        public static final int ORIGINAL_DISTRIBUTOR = 80;
        public static final int WINDOW_OPEN_CO = 81;
        public static final int PACKET_ENTRY = 10080;
        public static final int BOMBAY_REPAIR = 10080;
        public static final int PLAN_CHANGE = 10080;
        public static final int MULTY = 10080;
        public static final int DISPUTE = 10080;
        public static final int FLIP_PLAN = 10080;
    }

    public static class IRPendingActionType {

        public static final String PREDICTION_ENTER = "PRE";
        public static final String WEIGHT = "WEIGHT";
        public static final String POLKI = "POLKI";
        public static final String EXTRA = "EXTRA";
    }

    public static class DataType {

        public static final String INTEGER = "Integer";
        public static final String STRING = "String";
        public static final String BOOLEAN = "Boolean";
        public static final String DATE = "Date";
        public static final String TABLE = "Table";
    }

    public static class ParameterType {

        public static final String TEXT_BOX = "textbox";
        public static final String DATE_PICKER = "datepicker";
        public static final String TEXTAREA = "textarea";
        public static final String COMBO = "combo";
        public static final String MULTISELECT = "multiselect";
        public static final String CHECKBOX = "checkbox";
        public static final String RADIO = "radio";
        public static final String NUMBER = "number";
        public static final String TABLE = "table";
        public static final String CHECK_BOX_TABLE = "checkboxtable";
    }

    public interface RapCalcProcessType {

        String PLAN_CHANGE = "planChange";
        String MULTY = "multy";
        String DISPUTE = "dispute";
        String FLIP_PLAN = "flipPlan";
        String CHECKER = "checker";
    }

    public interface PredictionType {

        long RATE_CHANGE = 20051l;
        long ROUGH_MAKABLE = 20034l;
        long MAKABLE_CHECKER = 20035l;
        long FINAL_MAKABLE = 20036l;
        long ARTIST_LOOKING = 20037l;
        long MAKABLE_CHECKER_REPAIRING= 30063l;
        long MFG_MANAGER_ROUGH = 20038l;
        long ARTIST_FINAL = 20039l;
        long MANAGER_FINAL = 20040l;
        long POLISH_CHECKER = 20041l;
        long GRADER = 20042l;
        long ROUGH_PREDICTION = 20043l;
        long ESTIMATE_PREDICTION = 20044l;
        long TRAINEE_PREDICTION = 20045l;
        long LASER_BREAKING = 70441l;
        long GALAXY_SARIN = 20046l;
    }

    public static class ProcessBehaviour {

        public static final String CORE = "C";
        public static final String STOCK = "STOCK";
        public static final String TRANSPORT = "TRANS";
        public static final String TEMPARORY = "TEMP";
    }

    public static class Request {

        public static final Long MAIN_REQUEST = 1l;
        public static final Long BOMBAY_REPAIR_REQUEST = null;
        public static final Long DISPUTE = 3l;
        public static final Long MULTY = 3l;
        public static final Long PLAN_CANGE = 3l;
        public static final Long HIGH_AMOUNT_DIAMOND_REQUEST = null;
        public static final Long COLLECT_DIAMOND_REQUEST = null;
    }

    public static class ErrorMessages {

        public static final String CARAT_RANGE_EXCEPTION = "";
    }

    public static class ShapeCut {

        public static final String ADD = "add";
        public static final String REMOVE = "remove";
    }

    public static class CalcPolSym {

        public static final String CURRENT = "current";
        public static final String REMOVE = "remove";
    }

    public static class RType {

        public static final int NONE = 0;
        public static final int MIX = 5;
        public static final int ORDER = 10;
    }

    public interface FileTypes {

        String FINAL_PLAN = "FP";
        String ROUGH_MAKABLE = "RM";
        String SAWING = "SW";
        String FINAL_MAKABLE = "FM";
        String QUAZER = "QZ";
        String ORIGINAL = "OR";
        String MAKABLE = "MK";
        String POLISH_IMAGE = "PI";
        String COL_FLS_CHECK = "CFC";
        String GALAXY_MACHINE = "GLXM";
        String GALAXY_ARCHIVE_FILE_LOCATION = "GLXAO";
        String GALAXY_BACKUP_ORIGINAL_FILE_LOCATION = "GLXBO";
        String GALAXY_DONE_BACKUP_FILE_LOCATION = "GLXDB";
        String ROUGH_IMAGE = "RI";
        String HELIUM_POLISH = "HP";
        String PEL_16 = "PEL";
        String GRADING_PAGE = "GRP";
        String GALAXY_QC_TRANSFER = "GQT";
        String GALAXY_QC_OPERATOR_TRANSFER_READY = "GQOTR";
        String GALAXY_QC_NOT_IMPORTANT_PATH = "GQNIP";
        String GALAXY_QC_READY = "GQR";
        String GALAXY_QC_OPERATOR_CHECK_DONE = "GQOCD";
        String GALAXY_QC_CHECKER_CHECK_READY = "GQCCR";
        String GALAXY_QC_CHECKER_CHECK_DONE = "GQCCD";
        String GALAXY = "GLX";

    }

    public static class PrintType {

        public static final String TABLE_WISE_EMPLOYEE_SLIP = "TWES";
        public static final String CHECK_LIST_SLIP = "IRCL";
        public static final String ISSUE_RECEIVE_WITH_MACHINE_INFO = "IRM";
        public static final String ISSUE_RECEIVE_WITH_MAKABLE_CHECKER_PREDICTION = "IRPMC";
        public static final String ISSUE_RECEIVE_WITH_ARTIST_LOOKING_PREDICTION = "IRPALP";
        public static final String ISSUE_RECEIVE_WITH_PREDICTION = "IRP";
        public static final String SIMPLE_ISSUE_RECEIVE = "IRS";
        public static final String SIMPLE_ISSUE_RECEIVE_WITH_SHAPE_MANAGER = "IRSSM";
        public static final String GENERATE_SLIP_MULTIPLE_EMPLOYEE_WITH_MANAGER = "GSMS";
    }

    public interface HkEntityFields {

        String IS_ACTIVE = "isActive";
        String IS_ARCHIVE = "isArchive";
        String CREATED_ON = "createdOn";
        String CREATED_BY = "createdBy";
        String MODIFIED_ON = "modifiedOn";
        String MODIFIED_BY = "modifiedBy";

    }

    public interface DueDaysEndType {

        String ISSUE_TO_OTHER_PROCESS = "IOP";
        String PREDICTION = "P";
        String ISSUE_IN_PROCESS = "IIP";
    }

    public interface GenerateSlipType {

        String ISSUE_TO_EMPLOYEE = "I";
        String ISSUE_TO_REQUEST_ORIGINATOR = "O";
    }

    public interface GalaxyQcStaus {

        String READY_FOR_QC = "RFQ";
        String READY_FOR_OPERATOR_CHECK = "RFOC";
        String OPERATING_CHECKING = "OC";
        String READY_FOR_CHECKER_CHECK = "RFCC";
        String NOT_IMPORTANT = "RFCC";
        String CHECKER_CHECKING = "CC";
        String CHECKER_DONE = "CD";
        String REPAIRING = "R";
        String COMPLETE = "C";

    }

    public interface BarcodeType {

        String SIMPLE = "S";
        String FINAL_MAKABLE = "FM";
        String REPAIRING = "R";
    }

    public enum StorageType {
        CUSTOM,
        SIMPLE,
        LOT_CODE_WISE,
        LOT_SRNO_TAG_WISE
    }

    public interface JoinEntity {

        String getValue();
    }

    public static Criteria addFetchMode(Criteria c, JoinEntity[] joinEntitys) {
        for (JoinEntity joinEntity : joinEntitys) {
            c.setFetchMode(joinEntity.getValue(), FetchMode.JOIN);
        }
        return c;
    }

    public enum ProcedureResponse {

        HARD_PASS("HARD_PASS"),
        SOFT_PASS("SOFT_PASS"),
        HARD_FAIL("HARD_FAIL"),
        SOFT_FAIL("SOFT_FAIL");

        private String val;

        private ProcedureResponse(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

    }

    public interface PlanStatus {

        String MULTY = "Multy";
        String DISPUTE = "Dispute";
        String PLAN_CHANGE = "PChange";
    }

    /**
     * @_offset numeric,
     * @_limit numeric,
     * @_header bit
     */
    public interface PaginationParams {

        String HEADER = "@_header";
        String LIMIT = "@_limit";
        String OFFSET = "@_offset";

        public static String joinForInQuery() {
            StringBuilder builder = new StringBuilder();
            builder.append("('").append(LIMIT).append("',");
            builder.append("'").append(OFFSET).append("',");
            builder.append("'").append(HEADER).append("')");
            return builder.toString();
        }
    }

    public interface ParameterDetails {

        String OBJECT_ID = "objectId";
        String OBJECT_NAME = "objectName";
        String OBJECT_TYPE = "objectType";
        //
        String PARAMETER_ID = "parameterId";
        String PARAMETER_NAME = "parameterName";
        String PARAMETER_TYPE = "parameterDataType";
        String PARAMETER_MAX_SIZE = "parameterMaxBytes";
        String PARAMETER_IS_OUT = "isOutParameter";

    }

    public enum SlipType {
        GENERATE_SLIP, PACKET_ENTRY, ISSUE_RECEIVE, SLIP_MASTER
    }

    public enum SlotStatus {
        UPCOMING, STARTED, PROCESSED, SKIP
    }

}
