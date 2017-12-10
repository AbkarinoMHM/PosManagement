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

/********************* Pos Status ***********************/
INSERT INTO public.pos_status(
	pos_status_id, pos_status_name, pos_status_is_default)
	VALUES (1, 'Stocked', true);

/******************** Security ********************/
INSERT INTO public.policy(
	policy_id, policy_name)
	VALUES (1, '*:*');
INSERT INTO public.policy(
	policy_id, policy_name)
	VALUES (2, 'project:*');
INSERT INTO public.policy(
	policy_id, policy_name)
	VALUES (3, 'project:select');
INSERT INTO public.policy(
	policy_id, policy_name)
	VALUES (4, 'project:add');
INSERT INTO public.policy(
	policy_id, policy_name)
	VALUES (5, 'project:update');
INSERT INTO public.policy(
	policy_id, policy_name)
	VALUES (6, 'project:delete');

	