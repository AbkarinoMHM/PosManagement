/********************* settings *********************/
INSERT INTO public.setting(
	setting_name, setting_value)
	VALUES ('customerName', '');
	
INSERT INTO public.setting(
	setting_name, setting_value)
	VALUES ('vendorName', '');
	
INSERT INTO public.setting(
	setting_name, setting_value)
	VALUES ('customerClass', '');

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
	