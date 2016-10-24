CREATE database IF NOT EXISTS `EBE`;
USE `EBE`;

/********************* Addresses Information *********************/
CREATE TABLE `region` (
  `region_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `region_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `area` (
  `area_id` bigint(20) NOT NULL,
  `area_name` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `region_id` bigint(20) NOT NULL,
  PRIMARY KEY (`area_id`),
  KEY `Region_FK_idx` (`region_id`),
  CONSTRAINT `Region_FK` FOREIGN KEY (`region_id`) REFERENCES `region` (`region_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/****************************************************************/

/******************** Merchant Information *********************/
CREATE TABLE `merchant_class` (
`merchant_class_id` int(11) NOT NULL,
`merchant_class_name` char(10) NOT NULL,
 PRIMARY KEY (`merchant_class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `merchant` (
  `merchant_id` bigint(20) NOT NULL,
  `merchant_key` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `merchant_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `merchant_notes` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `merchant_contact_person_1` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `merchant_contact_tel_1` varchar(50) DEFAULT NULL,
  `merchant_contact_person_2` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `merchant_contact_tel_2` varchar(50) DEFAULT NULL,
  `merchant_contact_person_3` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `merchant_contact_tel_3` varchar(50) DEFAULT NULL,
  `merchant_class_id` int(11) DEFAULT NULL,
  `merchant_mcc` varchar(50) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`merchant_id`),
  KEY `merchant_class_FK_idx` (`merchant_class_id`),
  CONSTRAINT `merchant_class_FK` FOREIGN KEY (`merchant_class_id`) REFERENCES `merchant_class` (`merchant_class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `merchantBranch` (
  `merchant_branch_id` bigint(20) NOT NULL,
  `merchant_id` bigint(20) NOT NULL,
  `merchant_branch_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `merchant_branch_address` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `area_id` bigint(20) NOT NULL,
  `merchant_branch_notes` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`merchant_branch_id`),
  KEY `MerchantBranch_Area_FK_idx` (`area_id`),
  KEY `MerchantLocatation_Merchant_FK_idx` (`merchant_id`),
  CONSTRAINT `MerchantLocatation_Merchant_FK` FOREIGN KEY (`merchant_id`) REFERENCES `merchant` (`merchant_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `MerchantBranch_Area_FK` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/****************************************************************/

/*************************** EBE ********************************/
CREATE TABLE `project` (
  `project_id` bigint(20) NOT NULL,
  `project_name` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `ebeBranch` (
  `ebe_branch_id` bigint(20) NOT NULL,
  `ebe_branch_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_address` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_tel` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `area_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ebe_branch_id`),
  KEY `Branch_Area_FK_idx` (`area_id`),
  CONSTRAINT `Branch_Area_FK` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `servicecenter` (
  `service_center_id` bigint(20) NOT NULL,
  `service_center_name` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `service_center_address` longtext CHARACTER SET utf8mb4,
  `service_center_tel` varchar(15) DEFAULT NULL,
  `area_id` bigint(20) NOT NULL,
  PRIMARY KEY (`service_center_id`),
  KEY `ServiceCenter_Area_FK_idx` (`area_id`),
  CONSTRAINT `ServiceCenter__Area_FK` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `technician` (
  `technician_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `technician_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `technician_job_title` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `technician_rate` int(11) DEFAULT '0',
  `technician_mobile` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `technician_address` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `technician_tel` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`technician_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `operator` (
  `operator_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `operator_user_name` varchar(50) DEFAULT NULL,
  `operator_password` varchar(50) DEFAULT NULL,
  `operator_address` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `operator_mobile` varchar(50) DEFAULT NULL,
  `operator_tel` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`operator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `operatorbranch` (
  `operator_id` bigint(20) NOT NULL,
  `ebe_branch_id` bigint(20) NOT NULL,
  PRIMARY KEY (`operator_id`,`ebe_branch_id`),
  KEY `OperatorBranch_Operator_FK_idx` (`operator_id`),
  KEY `OperatorBranch_Branch_FK_idx` (`ebe_branch_id`),
  CONSTRAINT `OperatorBranch_Operator_FK` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`operator_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `OperatorBranch_Branch_FK` FOREIGN KEY (`ebe_branch_id`) REFERENCES `ebeBranch` (`ebe_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*DROP TABLE IF EXISTS `operatorposstatus`;
CREATE TABLE `operatorposstatus` (
  `operator_id` bigint(20) NOT NULL,
  `POSpos_status_id` int(11) NOT NULL,
  `IsEnabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`operator_id`,`POSpos_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
/****************************************************************/

/*************************** POS ********************************/

CREATE TABLE `simVendor` (
  `sim_vendor_id` int(11) NOT NULL AUTO_INCREMENT,
  `sim_vendor_name` varchar(50) NOT NULL,
  PRIMARY KEY (`sim_vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sim` (
  `sim_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sim_number` varchar(50) NOT NULL,
  `sim_vendor_id` int(11) NOT NULL,
  `ebe_branch_id` bigint(20) DEFAULT NULL,
  `sim_is_damage` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`sim_id`),
  KEY `Sim_SimVendor_FK_idx` (`sim_vendor_id`),
  KEY `Sim_Branch_FK_idx` (`ebe_branch_id`),
  CONSTRAINT `Sim_Branch_FK` FOREIGN KEY (`ebe_branch_id`) REFERENCES `ebeBranch` (`ebe_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Sim_SimVendor_FK` FOREIGN KEY (`sim_vendor_id`) REFERENCES `simVendor` (`sim_vendor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `posstatus` (
  `pos_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `pos_status_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`pos_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `postype` (
  `pos_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `pos_type_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`pos_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `poscondition` (
  `pos_condition_id` int(11) NOT NULL AUTO_INCREMENT,
  `pos_condition_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`pos_condition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pos` (
  `pos_id` bigint(20) NOT NULL,
  `pos_serial_number` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `pos_vendor` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_model` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_type_id` int(11) DEFAULT NULL,
  `pos_made` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_part_number` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_other_features` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_batch_number` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_condition_id` int(11) DEFAULT NULL,
  `pos_remarks` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_status_id` int(11) DEFAULT NULL,
  `merchant_branch_id` bigint(20) DEFAULT NULL,
  `pos_file_id` bigint(20) DEFAULT NULL,
  `pos_terminal_id` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_node_id` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_notes` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pos_tender_num` varchar(50) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `ebe_branch_id` bigint(20) DEFAULT NULL,
  `Timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pos_id`),
  KEY `Pos_PosType_FK_idx` (`pos_type_id`),
  KEY `Pos_PosStatus_FK_idx` (`pos_status_id`),
  KEY `Pos_MerchantBranch_FK_idx` (`merchant_branch_id`),
  KEY `Pos_Project_FK_idx` (`project_id`),
  KEY `Pos_Branch_FK_idx` (`ebe_branch_id`),
  KEY `Pos_PosCondition_Fk_idx` (`pos_condition_id`),
  CONSTRAINT `Pos_Branch_FK` FOREIGN KEY (`ebe_branch_id`) REFERENCES `ebebranch` (`ebe_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Pos_MerchantBranch_FK` FOREIGN KEY (`merchant_branch_id`) REFERENCES `merchantbranch` (`merchant_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Pos_PosCondition_Fk` FOREIGN KEY (`pos_condition_id`) REFERENCES `poscondition` (`pos_condition_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Pos_PosStatus_FK` FOREIGN KEY (`pos_status_id`) REFERENCES `posstatus` (`pos_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Pos_PosType_FK` FOREIGN KEY (`pos_type_id`) REFERENCES `postype` (`pos_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Pos_Project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `possim` (
  `pos_id` bigint(20) NOT NULL,
  `sim_id` bigint(20) NOT NULL,
  `pos_sim_is_primary` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`pos_id`,`sim_id`),
  KEY `pos_sim_sim_Fk_idx` (`sim_id`),
  CONSTRAINT `pos_sim_pos_FK` FOREIGN KEY (`pos_id`) REFERENCES `pos` (`pos_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pos_sim_sim_Fk` FOREIGN KEY (`sim_id`) REFERENCES `sim` (`sim_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `rollsize` (
  `roll_size_id` int(11) NOT NULL AUTO_INCREMENT,
  `roll_size_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,  
  PRIMARY KEY (`roll_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `roll` (
  `roll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roll_size_id` int(11) NOT NULL,
  `roll_quantaty` int(11) NOT NULL DEFAULT '0',
  `roll_direction` tinyint(1) NOT NULL DEFAULT '0',
  `ebe_branch_id` bigint(20) NOT NULL,
  `merchant_branch_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`roll_id`),
  KEY `Roll_RollSize_FK_idx` (`roll_size_id`),
  KEY `Roll_Branch_FK_idx` (`ebe_branch_id`),
  KEY `Roll_MerchantBranch_FK_idx` (`merchant_branch_id`),
  CONSTRAINT `Roll_Branch_FK` FOREIGN KEY (`ebe_branch_id`) REFERENCES `ebeBranch` (`ebe_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Roll_MerchantBranch_FK` FOREIGN KEY (`merchant_branch_id`) REFERENCES `MerchantBranch` (`merchant_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Roll_RollSize_FK` FOREIGN KEY (`roll_size_id`) REFERENCES `rollsize` (`roll_size_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/****************************************************************/

/************************** Items *******************************/

CREATE TABLE `itemType` (
  `item_type_id` INT(11) NOT NULL,
  `item_type_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`item_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `item` (
  `item_id` bigint(20) NOT NULL,
  `item_type_id` INT(11) NOT NULL,
  `item_serial_number` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `item_description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ebe_branch_id` bigint(20) NOT NULL,
  `TimeStamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `item_made` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `item_model` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `Item_Project_FK_idx` (`project_id`),
  KEY `Item_ItemType_FK_idx` (`item_type_id`),
  KEY `Item_Branch_FK_idx` (`ebe_branch_id`),
  CONSTRAINT `Item_Branch_FK` FOREIGN KEY (`ebe_branch_id`) REFERENCES `ebeBranch` (`ebe_branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Item_Project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Item_ItemType_FK` FOREIGN KEY (`item_type_id`) REFERENCES `itemType` (`item_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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




