CREATE SEQUENCE public.operator_operator_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.poscondition_pos_condition_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.posstatus_pos_status_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

 CREATE SEQUENCE public.postype_pos_type_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.region_region_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.roll_roll_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.rollsize_roll_size_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.sim_sim_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.simvendor_sim_vendor_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.technician_technician_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

/********************* Addresses Information *********************/
CREATE TABLE public.region
(
    region_id integer NOT NULL DEFAULT nextval('region_region_id_seq'::regclass),
    region_name character varying(50) ,
    CONSTRAINT region_pkey PRIMARY KEY (region_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.area
(
    area_id bigint NOT NULL,
    area_name character varying(50)  NOT NULL,
    region_id bigint NOT NULL,
    CONSTRAINT area_pkey PRIMARY KEY (area_id),
    CONSTRAINT area_region_id_fkey FOREIGN KEY (region_id)
        REFERENCES public.region (region_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
/****************************************************************/

/******************** Merchant Information *********************/
CREATE TABLE public.merchant_class
(
    merchant_class_id integer NOT NULL,
    merchant_class_name character(10)  NOT NULL,
    CONSTRAINT merchant_class_pkey PRIMARY KEY (merchant_class_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.merchant
(
    merchant_id bigint NOT NULL,
    merchant_key character varying(50)  NOT NULL,
    merchant_name character varying(100) ,
    merchant_notes character varying(100) ,
    merchant_contact_person_1 character varying(255) ,
    merchant_contact_tel_1 character varying(50) ,
    merchant_contact_person_2 character varying(255) ,
    merchant_contact_tel_2 character varying(50) ,
    merchant_contact_person_3 character varying(255) ,
    merchant_contact_tel_3 character varying(50) ,
    merchant_class_id integer NOT NULL,
    merchant_mcc character varying(50) ,
    "timestamp" timestamp without time zone,
    CONSTRAINT merchant_pkey PRIMARY KEY (merchant_id),
    CONSTRAINT merchant_merchant_class_id_fkey FOREIGN KEY (merchant_class_id)
        REFERENCES public.merchant_class (merchant_class_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.merchantbranch
(
    merchant_branch_id bigint NOT NULL,
    merchant_id bigint NOT NULL,
    merchant_branch_name character varying(50) ,
    merchant_branch_address character varying(100) ,
    area_id bigint NOT NULL,
    merchant_branch_notes character varying(100) ,
    CONSTRAINT merchantbranch_pkey PRIMARY KEY (merchant_branch_id),
    CONSTRAINT merchantbranch_area_id_fkey FOREIGN KEY (area_id)
        REFERENCES public.area (area_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT merchantbranch_merchant_id_fkey FOREIGN KEY (merchant_id)
        REFERENCES public.merchant (merchant_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
/****************************************************************/

/*************************** EBE ********************************/
CREATE TABLE public.project
(
    project_id bigint NOT NULL,
    project_name character varying(50)  NOT NULL,
    CONSTRAINT project_pkey PRIMARY KEY (project_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.ebebranch
(
    ebe_branch_id bigint NOT NULL,
    ebe_branch_name character varying(50) ,
    ebe_branch_address character varying(100) ,
    ebe_branch_tel character varying(50) ,
    area_id bigint NOT NULL,
    CONSTRAINT ebebranch_pkey PRIMARY KEY (ebe_branch_id),
    CONSTRAINT ebebranch_area_id_fkey FOREIGN KEY (area_id)
        REFERENCES public.area (area_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.servicecenter
(
    service_center_id bigint NOT NULL,
    service_center_name character varying(50)  NOT NULL,
    service_center_address text ,
    service_center_tel character varying(15) ,
    area_id bigint NOT NULL,
    CONSTRAINT servicecenter_pkey PRIMARY KEY (service_center_id),
    CONSTRAINT servicecenter_area_id_fkey FOREIGN KEY (area_id)
        REFERENCES public.area (area_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.technician
(
    technician_id integer NOT NULL DEFAULT nextval('technician_technician_id_seq'::regclass),
    technician_name character varying  NOT NULL,
    technician_job_title character varying(50) ,
    technician_rate integer,
    technician_mobile character varying(50) ,
    technician_address character varying(255) ,
    technician_tel character varying(50) ,
    CONSTRAINT technician_pkey PRIMARY KEY (technician_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.operator
(
    operator_id integer NOT NULL DEFAULT nextval('operator_operator_id_seq'::regclass),
    operator_name character varying(100) ,
    operator_user_name character varying(50) ,
    operator_password character varying(50) ,
    operator_address character varying(100) ,
    operator_mobile character varying(50) ,
    operator_tel character varying(50) ,
    CONSTRAINT operator_pkey PRIMARY KEY (operator_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.operatorbranch
(
    operator_id bigint NOT NULL,
    ebe_branch_id bigint NOT NULL,
    CONSTRAINT operatorbranch_pkey PRIMARY KEY (ebe_branch_id, operator_id),
    CONSTRAINT operatorbranch_ebe_branch_id_fkey FOREIGN KEY (ebe_branch_id)
        REFERENCES public.ebebranch (ebe_branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT operatorbranch_operator_id_fkey FOREIGN KEY (operator_id)
        REFERENCES public.operator (operator_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

/*DROP TABLE IF EXISTS `operatorposstatus`;
CREATE TABLE `operatorposstatus` (
  `operator_id` bigint(20) NOT NULL,
  `POSpos_status_id` int(11) NOT NULL,
  `IsEnabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`operator_id`,`POSpos_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
/****************************************************************/

/*************************** POS ********************************/

CREATE TABLE public.simvendor
(
    sim_vendor_id integer NOT NULL DEFAULT nextval('simvendor_sim_vendor_id_seq'::regclass),
    sim_vendor_name character varying(50)  NOT NULL,
    CONSTRAINT simvendor_pkey PRIMARY KEY (sim_vendor_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.sim
(
    sim_id integer NOT NULL DEFAULT nextval('sim_sim_id_seq'::regclass),
    sim_number character varying(50)  NOT NULL,
    sim_vendor_id integer NOT NULL,
    ebe_branch_id bigint NOT NULL,
    sim_is_damage smallint NOT NULL DEFAULT 0,
    CONSTRAINT sim_pkey PRIMARY KEY (sim_id),
    CONSTRAINT sim_ebe_branch_id_fkey FOREIGN KEY (ebe_branch_id)
        REFERENCES public.ebebranch (ebe_branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT sim_sim_vendor_id_fkey FOREIGN KEY (sim_vendor_id)
        REFERENCES public.simvendor (sim_vendor_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.posstatus
(
    pos_status_id integer NOT NULL DEFAULT nextval('posstatus_pos_status_id_seq'::regclass),
    pos_status_name character varying  NOT NULL,
    CONSTRAINT posstatus_pkey PRIMARY KEY (pos_status_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.postype
(
    pos_type_id integer NOT NULL DEFAULT nextval('postype_pos_type_id_seq'::regclass),
    pos_type_name character varying  NOT NULL,
    CONSTRAINT postype_pkey PRIMARY KEY (pos_type_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.poscondition
(
    pos_condition_id integer NOT NULL DEFAULT nextval('poscondition_pos_condition_id_seq'::regclass),
    pos_condition_name character varying  NOT NULL,
    CONSTRAINT poscondition_pkey PRIMARY KEY (pos_condition_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.pos
(
    pos_id bigint NOT NULL,
    pos_serial_number character varying(50)  NOT NULL,
    pos_vendor character varying(100) ,
    pos_model character varying(100) ,
    pos_type_id integer NOT NULL,
    pos_made character varying(100) ,
    pos_part_number character varying(100) ,
    pos_other_features character varying(100) ,
    pos_batch_number character varying(100) ,
    pos_condition_id integer NOT NULL,
    pos_remarks character varying(100) ,
    pos_status_id integer NOT NULL,
    merchant_branch_id bigint,
    pos_file_id bigint,
    pos_terminal_id character varying(50) ,
    pos_node_id character varying(50) ,
    pos_notes character varying(255) ,
    pos_tender_num character varying(50) ,
    project_id bigint NOT NULL,
    ebe_branch_id bigint NOT NULL,
    "Timestamp" timestamp without time zone NOT NULL,
    CONSTRAINT pos_pkey PRIMARY KEY (pos_id),
    CONSTRAINT pos_ebe_branch_id_fkey FOREIGN KEY (ebe_branch_id)
        REFERENCES public.ebebranch (ebe_branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pos_merchant_branch_id_fkey FOREIGN KEY (merchant_branch_id)
        REFERENCES public.merchantbranch (merchant_branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pos_pos_condition_id_fkey FOREIGN KEY (pos_condition_id)
        REFERENCES public.poscondition (pos_condition_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pos_pos_status_id_fkey FOREIGN KEY (pos_status_id)
        REFERENCES public.posstatus (pos_status_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pos_pos_type_id_fkey FOREIGN KEY (pos_type_id)
        REFERENCES public.postype (pos_type_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pos_project_id_fkey FOREIGN KEY (project_id)
        REFERENCES public.project (project_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.possim
(
    pos_id bigint NOT NULL,
    sim_id bigint NOT NULL,
    pos_sim_is_primary smallint NOT NULL DEFAULT 1,
    CONSTRAINT possim_pkey PRIMARY KEY (pos_id, sim_id),
    CONSTRAINT possim_pos_id_fkey FOREIGN KEY (pos_id)
        REFERENCES public.pos (pos_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT possim_sim_id_fkey FOREIGN KEY (sim_id)
        REFERENCES public.sim (sim_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.rollsize
(
    roll_size_id integer NOT NULL DEFAULT nextval('rollsize_roll_size_id_seq'::regclass),
    roll_size_name character varying  NOT NULL,
    CONSTRAINT rollsize_pkey PRIMARY KEY (roll_size_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


CREATE TABLE public.roll
(
    roll_id integer NOT NULL DEFAULT nextval('roll_roll_id_seq'::regclass),
    roll_size_id integer NOT NULL,
    roll_quantaty integer NOT NULL,
    roll_direction integer NOT NULL,
    ebe_branch_id bigint NOT NULL,
    merchant_branch_id bigint NOT NULL,
    CONSTRAINT roll_pkey PRIMARY KEY (roll_id),
    CONSTRAINT roll_ebe_branch_id_fkey FOREIGN KEY (ebe_branch_id)
        REFERENCES public.ebebranch (ebe_branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT roll_merchant_branch_id_fkey FOREIGN KEY (merchant_branch_id)
        REFERENCES public.merchantbranch (merchant_branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT roll_roll_size_id_fkey FOREIGN KEY (roll_size_id)
        REFERENCES public.rollsize (roll_size_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
/****************************************************************/

/************************** Items *******************************/

CREATE TABLE public.itemtype
(
    item_type_id integer NOT NULL,
    item_type_name character varying(50) ,
    CONSTRAINT itemtype_pkey PRIMARY KEY (item_type_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE public.item
(
    item_id bigint NOT NULL,
    item_type_id integer NOT NULL,
    item_serial_number character varying(50) ,
    item_description character varying(255) ,
    ebe_branch_id bigint NOT NULL,
    "TimeStamp" timestamp without time zone,
    item_made character varying(50) ,
    item_model character varying(50) ,
    project_id bigint,
    CONSTRAINT item_pkey PRIMARY KEY (item_id),
    CONSTRAINT item_ebe_branch_id_fkey FOREIGN KEY (ebe_branch_id)
        REFERENCES public.ebebranch (ebe_branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT item_item_type_id_fkey FOREIGN KEY (item_type_id)
        REFERENCES public.itemtype (item_type_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT item_project_id_fkey FOREIGN KEY (project_id)
        REFERENCES public.project (project_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

/*DROP TABLE IF EXISTS `pc`;
CREATE TABLE `pc` (
  `ID` bigint(20) NOT NULL,
  `SerialNumber` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_id` bigint(20) NOT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `made` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `model` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Pc_Project_FK_idx` (`project_id`),
  CONSTRAINT `Pc_Project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `pinpad`;
  CREATE TABLE `pinpad` (
  `ID` bigint(20) NOT NULL,
  `SerialNumber` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_id` bigint(20) NOT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `made` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `model` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
   `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
   KEY `Pinpad_Project_FK_idx` (`project_id`),
  CONSTRAINT `Pinpad_Project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `iccreader`;
CREATE TABLE `iccreader` (
  `ID` bigint(20) NOT NULL,
  `SerialNumber` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_id` int(11) NOT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Made` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Model` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
   `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
   KEY `IccReader_Project_FK_idx` (`project_id`),
  CONSTRAINT `IccReader_Project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `ups`;
CREATE TABLE `ups` (
  `ID` bigint(20) NOT NULL,
  `SerialNumber` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_id` int(11) NOT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `made` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `model` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
   KEY `ups_Project_FK_idx` (`project_id`),
  CONSTRAINT `ups_Project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `monitor`;
CREATE TABLE `monitor` (
  `ID` bigint(20) NOT NULL,
  `SerialNumber` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_id` int(11) NOT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `made` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `model` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Project`  bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `monitor_Project_FK_idx` (`project_id`),
  CONSTRAINT `monitor_Project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
/****************************************************************/

/*********************** Maintenance ****************************/
/*DROP TABLE IF EXISTS `maintenance`;
CREATE TABLE `maintenance` (
  `MaintenanceID` bigint(20) NOT NULL AUTO_INCREMENT,
  `pos_id` bigint(20) NOT NULL,
  `MoveDate` varchar(20) NOT NULL,
  `StartDate` varchar(20) DEFAULT NULL,
  `ApproveDate` varchar(20) DEFAULT NULL,
  `EndDate` varchar(20) DEFAULT NULL,
  `MoveBy` int(11) DEFAULT NULL,
  `StartBy` int(11) DEFAULT NULL,
  `ApproveBy` int(11) DEFAULT NULL,
  `EndBy` int(11) DEFAULT NULL,
  `LaborPrice` bigint(20) DEFAULT '0',
  `SparePartsPrice` bigint(20) DEFAULT '0',
  `Timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `StartComment` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ApproveRemarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `StockedDate` varchar(20) DEFAULT NULL,
  `StockedBy` int(11) DEFAULT NULL,
  `service_center_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaintenanceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `sparepart`;
CREATE TABLE `sparepart` (
  `SparePartID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Price` varchar(10) DEFAULT NULL,
  `ebe_branch_id` bigint(20) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`SparePartID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `maintenancesparepart`;
CREATE TABLE `maintenancesparepart` (
  `MaintenanceID` bigint(20) NOT NULL,
  `SparePartID` bigint(20) NOT NULL,
  PRIMARY KEY (`MaintenanceID`,`SparePartID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
/****************************************************************/

/*********************** Deployment ****************************/
/*DROP TABLE IF EXISTS `posdeployment`;
CREATE TABLE `posdeployment` (
  `DeploymentID` bigint(20) NOT NULL,
  `pos_id` bigint(20) NOT NULL,
  `StockedDate` datetime(6) NOT NULL,
  `StockedBy` bigint(20) NOT NULL,
  `DeployedDate` datetime(6) DEFAULT NULL,
  `DeployedBy` bigint(20) DEFAULT NULL,
  `ConfiguredDate` datetime(6) DEFAULT NULL,
  `ConfiguredBy` bigint(20) DEFAULT NULL,
  `InitializedDate` datetime(6) DEFAULT NULL,
  `InitializedBy` bigint(20) DEFAULT NULL,
  `TransietDate` datetime(6) DEFAULT NULL,
  `TransietBy` bigint(20) DEFAULT NULL,
  `DoneDate` datetime(6) DEFAULT NULL,
  `DoneBy` bigint(20) DEFAULT NULL,
  `TechID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DeploymentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
/*DROP TABLE IF EXISTS `simdeployment`;
CREATE TABLE `simdeployment` (
  `SimDeploymentID` bigint(20) NOT NULL,
  `OrderID` varchar(20) NOT NULL,
  `operator_id` int(11) NOT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `SimKey` bigint(20) NOT NULL,
  PRIMARY KEY (`SimKey`,`SimDeploymentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
/*DROP TABLE IF EXISTS `posreplacement`;
CREATE TABLE `posreplacement` (
  `ReplacementID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Originalpos_id` bigint(20) NOT NULL,
  `Replacementpos_id` bigint(20) NOT NULL,
  `StartOfReplacement` datetime(6) NOT NULL,
  `EndOfReplacement` datetime(6) DEFAULT NULL,
  `StartReplacementBy` bigint(20) DEFAULT NULL,
  `DeploymentID` bigint(20) NOT NULL,
  `EndOfReplacementBy` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`ReplacementID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
/****************************************************************/

/************************ Ticketing *****************************/
/*DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `TicketID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_id` bigint(20) DEFAULT NULL,
  `operator_id` bigint(20) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `Priority` int(11) DEFAULT NULL,
  `TimeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pos_type_id` int(11) DEFAULT NULL,
  `TicketCategoryID` int(11) DEFAULT NULL,
  `CallerName` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `CallerTel` varchar(20) DEFAULT NULL,
  `TicketSourceID` int(11) DEFAULT NULL,
  PRIMARY KEY (`TicketID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `ticketCategory`;
CREATE TABLE `ticketCategory` (
  `ticketCategoryID` int(11) NOT NULL,
  `TicketCategoryName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ticketCategoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;*/

/*DROP TABLE IF EXISTS `ticketcomment`;
CREATE TABLE `ticketcomment` (
  `TicketCommentID` bigint(20) NOT NULL,
  `TicketID` bigint(20) DEFAULT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `operator_id` int(11) DEFAULT NULL,
  `TechnicalID` int(11) DEFAULT NULL,
  `Comment` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Priority` int(11) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  PRIMARY KEY (`TicketCommentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `tickethistory`;
CREATE TABLE `tickethistory` (
  `TicketHistoryID` bigint(20) NOT NULL,
  `TicketID` bigint(20) NOT NULL,
  `Status` int(11) NOT NULL,
  `Priority` int(11) DEFAULT NULL,
  `TimeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Comment` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `operator_id` bigint(20) DEFAULT NULL,
  `technician_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TicketHistoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `ticketpriorityhistory`;
CREATE TABLE `ticketpriorityhistory` (
  `TicketPriorityHistoryID` bigint(20) NOT NULL,
  `TicketID` bigint(20) DEFAULT NULL,
  `Priority` int(11) DEFAULT NULL,
  `TimeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Comment` varchar(250) CHARACTER SET utf8mb4 DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`TicketPriorityHistoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `TicketSource`;
CREATE TABLE `TicketSource` (
  `TicketSourceID` int(11) NOT NULL,
  `TicketSourceName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TicketSourceID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;*/

/*DROP TABLE IF EXISTS `ticketstatus`;
CREATE TABLE `ticketstatus` (
  `Ticketpos_status_id` int(11) NOT NULL,
  `Status` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`Ticketpos_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/*DROP TABLE IF EXISTS `tickettype`;
CREATE TABLE `tickettype` (
  `Ticketpos_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `TicketTypeName` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `SolutionSteps` varchar(1024) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`Ticketpos_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/********************************************************************/




