-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

/********************* settings *********************/
INSERT INTO public.setting(
	setting_name, setting_value)
	VALUES ('customerName', 'NSGB');
	
INSERT INTO public.setting(
	setting_name, setting_value)
	VALUES ('vendorName', 'EBE');
	
INSERT INTO public.setting(
	setting_name, setting_value)
	VALUES ('customerClass', 'A');

/********************* Pos Condition *********************/
INSERT INTO public.pos_condition(
	pos_condition_id, pos_condition_name)
	VALUES (1, 'New');
INSERT INTO public.pos_condition(
	pos_condition_id, pos_condition_name)
	VALUES (2, 'Used');

/******************** Security ********************/
INSERT INTO public.role(
	role_id, role_name)
	VALUES (1, 'ADMIN');

INSERT INTO public.role(
	role_id, role_name)
	VALUES (2, 'SUPERUSER');

INSERT INTO public.role(
	role_id, role_name)
	VALUES (3, 'USER');	
	