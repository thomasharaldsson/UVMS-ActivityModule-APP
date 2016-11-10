create table activity.activity_aap_process (id  serial not null, conversion_factor int4, fa_catch_id int4, primary key (id))
create table activity.activity_aap_process_code (id  serial not null, type_code varchar(255) not null, type_code_list_id varchar(255) not null, aap_process_id int4, primary key (id))
create table activity.activity_aap_product (id  serial not null, calculated_packaging_unit_count float8, calculated_packaging_weight float8, calculated_unit_quantity float8, calculated_weight_measure float8, packaging_type_code varchar(255), packaging_type_code_list_id varchar(255), packaging_unit_avarage_weight float8, packaging_unit_count float8, packaging_unit_count_code varchar(255), packaging_weight_unit_code varchar(255), species_code varchar(255), spacies_code_list_id varchar(255), unit_quantity float8, unit_quantity_code varchar(255), usage_code varchar(255), usage_code_list_id varchar(255), weighing_means_code varchar(255), weighting_means_code_list_id varchar(255), weight_measure float8, weight_measure_unit_code varchar(255), aap_process_id int4, primary key (id))
create table activity.activity_aap_stock (id  serial not null, stock_id varchar(255), stock_scheme_id varchar(255), fa_catch_id int4, primary key (id))
create table activity.activity_configuration (id  bigserial not null, config_name varchar(255), config_value varchar(255), primary key (id))
create table activity.activity_contact_party (id  serial not null, contact_person_id int4 not null, vessel_transport_means_id int4, primary key (id))
create table activity.activity_contact_party_role (id  serial not null, role_code varchar(255) not null, role_code_list_id varchar(255) not null, contact_party_id int4, primary key (id))
create table activity.activity_contact_person (id  serial not null, alias varchar(255), family_name varchar(255), family_name_prefix varchar(255), gender varchar(255), given_name varchar(255), middle_name varchar(255), name_suffix varchar(255), title varchar(255), primary key (id))
create table activity.activity_delimited_period (id  serial not null, calculated_duration float8, duration float8, duration_unit_code varchar(255), end_date timestamp, start_date timestamp, fishing_activity_id int4, fishing_trip_id int4, primary key (id))
create table activity.activity_fa_catch (id  serial not null, calculated_unit_quantity float8, calculated_weight_measure float8, species_code varchar(255) not null, species_code_listid varchar(255) not null, type_code varchar(255) not null, type_code_list_id varchar(255) not null, unit_quantity float8, unit_quantity_code varchar(255), usage_code varchar(255), usage_code_list_id varchar(255), weighing_means_code varchar(255), weighing_means_code_list_id varchar(255), weight_measure float8, weight_measure_unit_code varchar(255), fishing_activity_id int4, size_distribution_id int4 not null, primary key (id))
create table activity.activity_fa_report_document (id  serial not null, accepted_datetime timestamp, fmc_marker varchar(255), fmc_marker_list_id varchar(255), geom GEOMETRY, source varchar(255), status varchar(255), type_code varchar(255), type_code_list_id varchar(255), flux_report_document_id int4 not null, vessel_transport_means_id int4, primary key (id))
create table activity.activity_fa_report_identifier (id  serial not null, fa_report_identifier_id varchar(255), fa_report_identifier_scheme_id varchar(255), fa_report_document_id int4, primary key (id))
create table activity.activity_fishing_activity (id  serial not null, calculated_fishing_duration float8, calculated_operation_quantity float8, fishery_type_code varchar(255), fishery_type_code_list_id varchar(255), fishing_duration_measure float8, fishing_duration_measure_code varchar(255), geom GEOMETRY, occurence timestamp, operation_quantity float8, operation_quantity_code varchar(255), reason_code varchar(255), reason_code_list_id varchar(255), species_target_code varchar(255), species_target_code_list_id varchar(255), type_code varchar(255) not null, type_code_listid varchar(255) not null, vessel_activity_code varchar(255), vessel_activity_code_list_id varchar(255), dest_vessel_char_id int4, fa_report_document_id int4, related_fishing_activity_id int4, source_vessel_char_id int4, primary key (id))
create table activity.activity_fishing_activity_identifier (id  serial not null, fa_identifier_id varchar(255), fa_identifier_scheme_id varchar(255), fishing_activity_id int4, primary key (id))
create table activity.activity_fishing_gear (id  serial not null, type_code varchar(255) not null, type_code_list_id varchar(255) not null, fa_catch_id int4, fishing_activity_id int4, gear_problem_id int4, primary key (id))
create table activity.activity_fishing_gear_role (id  serial not null, role_code varchar(255) not null, role_code_list_id varchar(255) not null, fishing_gear_id int4, primary key (id))
create table activity.activity_fishing_trip (id  serial not null, type_code varchar(255), type_code_list_id varchar(255), fa_catch_id int4, fishing_activity_id int4, primary key (id))
create table activity.activity_fishing_trip_identifier (id  serial not null, trip_id varchar(255) not null, trip_scheme_id varchar(255) not null, fishing_trip_id int4, primary key (id))
create table activity.activity_flap_document (id  serial not null, flap_document_id varchar(255), flap_document_scheme_id varchar(255), fishing_activity_id int4, vessel_transport_means_id int4, primary key (id))
create table activity.activity_flux_characteristic (id  serial not null, calculated_value_measure float8, calculated_value_quantity float8, description text, description_language_id varchar(255), type_code varchar(255) not null, type_code_list_id varchar(255) not null, value_code varchar(255), value_date_time timestamp, value_indicator varchar(255), value_language_id varchar(255), value_measure float8, value_measure_unit_code varchar(255), value_quantity float8, value_quantity_code varchar(255), value_text text, fa_catch_id int4, fishing_activity_id int4, specified_flap_document_id int4, specified_flux_location_id int4, primary key (id))
create table activity.activity_flux_location (id  serial not null, altitude float8, country_id varchar(255), country_id_scheme_id varchar(255), flux_location_identifier varchar(255), flux_location_identifier_scheme_id varchar(255), flux_location_type varchar(255) not null, geom GEOMETRY, geopolitical_region_code varchar(255), geopolitical_region_code_list_id varchar(255), jurisdiction_country_code varchar(255), latitude float8, longitude float8, name text, name_laguage_id varchar(255), rfmo_code varchar(255), sovereign_rights_country_code varchar(255), system_id varchar(255), type_code varchar(255) not null, type_code_list_id varchar(255) not null, fa_catch_id int4, fishing_activity_id int4, primary key (id))
create table activity.activity_flux_party (id  serial not null, flux_party_name varchar(255), name_language_id varchar(255), primary key (id))
create table activity.activity_flux_party_identifier (id  serial not null, flux_party_identifier_id varchar(255), flux_party_identifier_scheme_id varchar(255), flux_party_id int4, primary key (id))
create table activity.activity_flux_report_document (id  serial not null, creation_datetime timestamp not null, purpose text, purpose_code varchar(255) not null, purpose_code_list_id varchar(255) not null, reference_id varchar(255), reference_scheme_id varchar(255), flux_party_id int4, primary key (id))
create table activity.activity_flux_report_identifier (id  serial not null, flux_report_identifier_id varchar(255), flux_report_identifier_scheme_id varchar(255), flux_report_document_id int4, primary key (id))
create table activity.activity_gear_characteristic (id  serial not null, calculated_value_measure float8, calculated_value_quantity float8, desc_language_id varchar(255), description varchar(255), type_code varchar(255) not null, type_code_list_id varchar(255) not null, value_code varchar(255), value_date_time timestamp, value_indicator varchar(255), value_measure float8, value_measure_unit_code varchar(255), value_quantity float8, value_quantity_code varchar(255), value_text varchar(255), fishing_gear_id int4, primary key (id))
create table activity.activity_gear_problem (id  serial not null, affected_quantity int4 not null, type_code varchar(255) not null, type_code_list_id varchar(255) not null, fishing_activity_id int4, primary key (id))
create table activity.activity_gear_problem_recovery (id  serial not null, recovery_measure_code varchar(255) not null, recovery_measure_code_list_id varchar(255) not null, gear_problem_id int4, primary key (id))
create table activity.activity_registration_event (id  serial not null, desc_language_id varchar(255), description text, occurrence_datetime timestamp, registration_location_id int4 not null, primary key (id))
create table activity.activity_registration_location (id  serial not null, desc_language_id varchar(255), description text, location_country_id varchar(255), location_country_scheme_id varchar(255), name text, name_language_id varchar(255), region_code varchar(255), region_code_list_id varchar(255), type_code varchar(255), type_code_list_id varchar(255), primary key (id))
create table activity.activity_size_distribution (id  serial not null, category_code varchar(255), category_code_list_id varchar(255), primary key (id))
create table activity.activity_size_distribution_classcode (id  serial not null, class_code varchar(255) not null, class_code_list_id varchar(255) not null, size_distribution_id int4, primary key (id))
create table activity.activity_structured_address (id  serial not null, address_id varchar(255), block_name varchar(1000), building_name varchar(1000), city_name varchar(255), city_subdivision_name varchar(255), country varchar(255), country_name varchar(255), country_subdivision_name varchar(255), plot_id varchar(1000), post_office_box varchar(255), postcode varchar(255), streetname varchar(1000), structured_address_type varchar(255), contact_party_id int4, flux_location_id int4, primary key (id))
create table activity.activity_vessel_identifier (id  serial not null, vessel_identifier_id varchar(255), vessel_identifier_scheme_id varchar(255), vessel_transport_mean_id int4, primary key (id))
create table activity.activity_vessel_storage_char_code (id  serial not null, vessel_type_code varchar(255), vessel_type_code_list_id varchar(255), vessel_storage_char_id int4, primary key (id))
create table activity.activity_vessel_storage_characteristics (id  serial not null, vessel_id varchar(255), vessel_scheme_id varchar(255), primary key (id))
create table activity.activity_vessel_transport_means (id  serial not null, country varchar(255), country_scheme_id varchar(255), name text, role_code varchar(255), role_code_list_id varchar(255), registration_event_id int4, primary key (id))
create table activity.mdr_action_type (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_codelist_status (id  bigserial not null, last_attempt timestamp, last_status varchar(255), last_success timestamp, object_acronym varchar(255), object_description varchar(255), object_name varchar(255), object_source varchar(255), schedulable varchar(255), end_date timestamp, start_date timestamp, versions varchar(255), primary key (id))
create table activity.mdr_conversion_factor (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), collective boolean, country varchar(255), factor float8, presentation varchar(255), source varchar(255), species varchar(255), state varchar(255), primary key (id))
create table activity.mdr_cr_fishing_category_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), sovereignty_waters_report_declaration varchar(255), primary key (id))
create table activity.mdr_cr_landing_countries_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_landing_indicator_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_landing_places (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_nafo_stock (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, area_code varchar(255), area_description varchar(255), species_code varchar(255), species_name varchar(255), primary key (id))
create table activity.mdr_cr_neafc_fishing_grounds (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_neafc_stocks (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_report_indicator_list (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_reporting_countries_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_reporting_period_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_cr_units_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_effort_target_species_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_effort_zones_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), legal_reference varchar(255), primary key (id))
create table activity.mdr_ers_events_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), presentation varchar(255), primary key (id))
create table activity.mdr_ers_gear_type_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), group_name varchar(255), sub_group_name varchar(255), primary key (id))
create table activity.mdr_ers_message_type_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_ers_reason_for_return_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_ers_return_status_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_ers_senders_recipients_list (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_fao_area_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, area varchar(255), description varchar(255), division varchar(255), subarea varchar(255), subdivision varchar(255), unit varchar(255), primary key (id))
create table activity.mdr_fish_freshness_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_fish_presentation_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), presentation varchar(255), primary key (id))
create table activity.mdr_fish_preservation_state_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_fish_size_category_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_gear_type_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_gfcm_statistical_rectangles_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), east float8, north float8, south float8, west float8, primary key (id))
create table activity.mdr_ices_statistical_rectangles_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, ices_name varchar(255), east float8, north float8, south float8, west float8, primary key (id))
create table activity.mdr_location_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), airport boolean, border_crossing_function boolean, commercial_port boolean, coordinates varchar(255), fishing_port boolean, fixed_transport_functions boolean, iso3_country_code varchar(255), landing_place boolean, latitude float8, longitude float8, multimodal_functions boolean, port boolean, postal_exchange_office boolean, rail boolean, road boolean, un_function_code varchar(255), unknown_function boolean, unlo_code varchar(255), primary key (id))
create table activity.mdr_means_of_weight_measuring_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_producers_organization_use_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_product_destination_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_regional_fisheries_management_organizations_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_relocation_destination_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_sender_type_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_sovereignty_waters_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), iso3_code varchar(255), primary key (id))
create table activity.mdr_species_iso3_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, author varchar(255), code varchar(255), english_name varchar(255), family varchar(255), french_name varchar(255), iso_order varchar(255), scientific_name varchar(255), spanish_name varchar(255), primary key (id))
create table activity.mdr_territory_and_currency_iso_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, currency_code varchar(255), currency_definition varchar(255), iso3_code varchar(255), iso4_code varchar(255), territory_name varchar(255), primary key (id))
create table activity.mdr_vessel_activity_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
create table activity.mdr_vessel_type_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), vessel_type varchar(255), primary key (id))
create table activity.mdr_withdrawn_codes (id  bigserial not null, created_on timestamp not null, refreshable varchar(1), end_date timestamp, start_date timestamp, code varchar(255), description varchar(255), primary key (id))
alter table activity.activity_aap_process add constraint FK_dakf3l3t13vybujolraorc4fv foreign key (fa_catch_id) references activity.activity_fa_catch
alter table activity.activity_aap_process_code add constraint FK_q2bbymdvhvtwuvglkmw0e3sv6 foreign key (aap_process_id) references activity.activity_aap_process
alter table activity.activity_aap_product add constraint FK_sqdted1a42nevbpana5op5i2r foreign key (aap_process_id) references activity.activity_aap_process
alter table activity.activity_aap_stock add constraint FK_mknpnly9p17enn7pje5s863hl foreign key (fa_catch_id) references activity.activity_fa_catch
alter table activity.activity_contact_party add constraint FK_jjes31te9vie691epqcer64n9 foreign key (contact_person_id) references activity.activity_contact_person
alter table activity.activity_contact_party add constraint FK_lxsftm3f7yrk458efn1vcnddw foreign key (vessel_transport_means_id) references activity.activity_vessel_transport_means
alter table activity.activity_contact_party_role add constraint FK_fy0bbekwy7rx41arphgdocy68 foreign key (contact_party_id) references activity.activity_contact_party
alter table activity.activity_delimited_period add constraint FK_9qnd6oedi9wsgt1yq841943jr foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_delimited_period add constraint FK_c3ec3buddl3otmyly9amqiw74 foreign key (fishing_trip_id) references activity.activity_fishing_trip
alter table activity.activity_fa_catch add constraint FK_ahye6hkrcgrqb095bjsrbpn3b foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_fa_catch add constraint FK_bo0j0a5j6nfrt69rf3exje6px foreign key (size_distribution_id) references activity.activity_size_distribution
alter table activity.activity_fa_report_document add constraint FK_sork3knkqrx4wcm5rtjmli328 foreign key (flux_report_document_id) references activity.activity_flux_report_document
alter table activity.activity_fa_report_document add constraint FK_qhwc5na98f22mugubyycgxnbw foreign key (vessel_transport_means_id) references activity.activity_vessel_transport_means
alter table activity.activity_fa_report_identifier add constraint FK_oc6aamgbnwi0pm41p3ap6eek5 foreign key (fa_report_document_id) references activity.activity_fa_report_document
alter table activity.activity_fishing_activity add constraint FK_lmwc0u83km0g4dsov355nssi6 foreign key (dest_vessel_char_id) references activity.activity_vessel_storage_characteristics
alter table activity.activity_fishing_activity add constraint FK_6929r5uvq3h7orbhw29smkrbf foreign key (fa_report_document_id) references activity.activity_fa_report_document
alter table activity.activity_fishing_activity add constraint FK_fje8fd73ml9hsyc6hg7bawu9v foreign key (related_fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_fishing_activity add constraint FK_eh8oklh6ofel12ccs2oxuwl0i foreign key (source_vessel_char_id) references activity.activity_vessel_storage_characteristics
alter table activity.activity_fishing_activity_identifier add constraint FK_dkvdh6p4wurd5ojej1958x7e7 foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_fishing_gear add constraint FK_r6e2tph33knht785wnm59vpwt foreign key (fa_catch_id) references activity.activity_fa_catch
alter table activity.activity_fishing_gear add constraint FK_lcn3j75n25sh2bi7gojsjj5xx foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_fishing_gear add constraint FK_i4h3xo72rddt28pbr705urd3x foreign key (gear_problem_id) references activity.activity_gear_problem
alter table activity.activity_fishing_gear_role add constraint FK_m0xq8cqo98x5lcrvccgrfgyu6 foreign key (fishing_gear_id) references activity.activity_fishing_gear
alter table activity.activity_fishing_trip add constraint FK_cqspacker069ildnny30md9td foreign key (fa_catch_id) references activity.activity_fa_catch
alter table activity.activity_fishing_trip add constraint FK_t932u27cfe4ma7kci5lw54nch foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_fishing_trip_identifier add constraint FK_l89eypgvjnlhub7307br13knb foreign key (fishing_trip_id) references activity.activity_fishing_trip
alter table activity.activity_flap_document add constraint FK_7ffdh71j68pdhsineue6em4qh foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_flap_document add constraint FK_pcv8a3yny9audhl625tygxequ foreign key (vessel_transport_means_id) references activity.activity_vessel_transport_means
alter table activity.activity_flux_characteristic add constraint FK_43yf41jruos068lnerku4tn4b foreign key (fa_catch_id) references activity.activity_fa_catch
alter table activity.activity_flux_characteristic add constraint FK_prdwqe2cuvsr9ulevccvqd4nx foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_flux_characteristic add constraint FK_jr4e6vssq7gihrl76d8988m97 foreign key (specified_flap_document_id) references activity.activity_flap_document
alter table activity.activity_flux_characteristic add constraint FK_8mwefc91noj8b5311y8s0ubu foreign key (specified_flux_location_id) references activity.activity_flux_location
alter table activity.activity_flux_location add constraint FK_6jn9j8xoumqwayqguo44pqdfu foreign key (fa_catch_id) references activity.activity_fa_catch
alter table activity.activity_flux_location add constraint FK_ma5ivkg56xrlw98vpylbqs0w8 foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_flux_party_identifier add constraint FK_mbtdcobucumplq069cluesjpx foreign key (flux_party_id) references activity.activity_flux_party
alter table activity.activity_flux_report_document add constraint FK_58x2dbmh036mhw664sfc1b63e foreign key (flux_party_id) references activity.activity_flux_party
alter table activity.activity_flux_report_identifier add constraint FK_k3tjybghru0n8cfjxvgoh302g foreign key (flux_report_document_id) references activity.activity_flux_report_document
alter table activity.activity_gear_characteristic add constraint FK_fgu4b6jfaocp5t6gln32x4lby foreign key (fishing_gear_id) references activity.activity_fishing_gear
alter table activity.activity_gear_problem add constraint FK_re5n555wr29x92uud0edjc3g4 foreign key (fishing_activity_id) references activity.activity_fishing_activity
alter table activity.activity_gear_problem_recovery add constraint FK_9ppmetwi1qrmvfd93qg7o3rtr foreign key (gear_problem_id) references activity.activity_gear_problem
alter table activity.activity_registration_event add constraint FK_32fq48ps69r3uf7efehms7hrm foreign key (registration_location_id) references activity.activity_registration_location
alter table activity.activity_size_distribution_classcode add constraint FK_g3t2rghpx7wuer81e4ea9gje7 foreign key (size_distribution_id) references activity.activity_size_distribution
alter table activity.activity_structured_address add constraint FK_cqjyt9q2qftixlit850l9w77x foreign key (contact_party_id) references activity.activity_contact_party
alter table activity.activity_structured_address add constraint FK_3es4qdro7nf3escmbiaa9hr5a foreign key (flux_location_id) references activity.activity_flux_location
alter table activity.activity_vessel_identifier add constraint FK_d1inu8erja7kn827laybe73d5 foreign key (vessel_transport_mean_id) references activity.activity_vessel_transport_means
alter table activity.activity_vessel_storage_char_code add constraint FK_dsxmnrc2f9heik015i91jbqj2 foreign key (vessel_storage_char_id) references activity.activity_vessel_storage_characteristics
alter table activity.activity_vessel_transport_means add constraint FK_jcxv86aao723c385tsipcvhw3 foreign key (registration_event_id) references activity.activity_registration_event
