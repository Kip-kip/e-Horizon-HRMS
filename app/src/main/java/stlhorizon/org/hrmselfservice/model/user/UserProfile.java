//package stlhorizon.org.hrmselfservice.model.user;
//
//
///**
// * Created by makari on 3/28/2018.
// */
//
//public class UserProfile {
//    private String username;
//    private String password;
//    private String code;
//    private String party_id;
//    private String salutation;
//    private String first_name;
//    private String middle_name;
//    private String last_name;
//    private String preferred_name;
//    private String gender;
//    private String contact_person;
//    private String dob;
//    private String national_id;
//    private String company_type;
//    private String entity_id;
//    private String personal_id;
//    private String alternate_name;
//    private String religion;
//    private String blood_group;
//    private String subcaste;
//    private String married;
//    private String secondment;
//    private String secondment_org_id;
//    private String service_initiation_id;
//    private String record_type;
//    private String service_type;
//    private String order_no;
//    private String order_date;
//    private String order_source;
//    private String order_sent_on;
//    private String order_accepted_on;
//    private String acknowledge_received_on;
//    private String document_no;
//    private String service_initiation_appoint_id;
//    private String order_reason;
//    private String order_reason_type;
//    private String service_effected_on;
//    private String service_end_on;
//    private String service_status;
//    private String approval_process_id;
//    private String approval_by;
//    private String approval_on;
//    private String approval_reference;
//    private String wef;
//    private String wet;
//    private String appoint_id;
//    private String appointment_type;
//    private String nature_of_employment;
//    private String proposed_joining_date;
//    private String date_of_join;
//    private String proposed_probation_end_date;
//    private String revised_probation_end_date;
//    private String probation_end_date;
//    private String confirmation_on;
//    private String estimated_retirement_date;
//    private String revised_retirement_date;
//    private String estimated_date_of_exit;
//    private String date_of_exit;
//    private String exit_type;
//    private String exit_reason;
//    private String joining_position;
//    private String joining_entity_id;
//    private String joining_entity_structure_id;
//    private String joining_sub_grade;
//    private String current_position;
//    private String current_entity_structure_id;
//    private String current_sub_grade;
//    private String recruitment_id;
//    private String final_settlement_status;
//    private String remark;
//    private String reappoint_id;
//    private String service_id;
//    private String email;
//    private String phone_no;
//    private String nssf;
//    private String nhif;
//    private String kra;
//    private String grade;
//    private String department;
//    public DependantsResponse dependants;
//
//    public UserProfile() {
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getParty_id() {
//        return party_id;
//    }
//
//    public void setParty_id(String party_id) {
//        this.party_id = party_id;
//    }
//
//    public String getSalutation() {
//        return salutation;
//    }
//
//    public void setSalutation(String salutation) {
//        this.salutation = salutation;
//    }
//
//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public String getMiddle_name() {
//        return middle_name;
//    }
//
//    public void setMiddle_name(String middle_name) {
//        this.middle_name = middle_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public String getPreferred_name() {
//        return preferred_name;
//    }
//
//    public void setPreferred_name(String preferred_name) {
//        this.preferred_name = preferred_name;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getContact_person() {
//        return contact_person;
//    }
//
//    public void setContact_person(String contact_person) {
//        this.contact_person = contact_person;
//    }
//
//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
//
//    public String getNational_id() {
//        return national_id;
//    }
//
//    public void setNational_id(String national_id) {
//        this.national_id = national_id;
//    }
//
//    public String getCompany_type() {
//        return company_type;
//    }
//
//    public void setCompany_type(String company_type) {
//        this.company_type = company_type;
//    }
//
//    public String getEntity_id() {
//        return entity_id;
//    }
//
//    public void setEntity_id(String entity_id) {
//        this.entity_id = entity_id;
//    }
//
//    public String getPersonal_id() {
//        return personal_id;
//    }
//
//    public void setPersonal_id(String personal_id) {
//        this.personal_id = personal_id;
//    }
//
//    public String getAlternate_name() {
//        return alternate_name;
//    }
//
//    public void setAlternate_name(String alternate_name) {
//        this.alternate_name = alternate_name;
//    }
//
//    public String getReligion() {
//        return religion;
//    }
//
//    public void setReligion(String religion) {
//        this.religion = religion;
//    }
//
//    public String getBlood_group() {
//        return blood_group;
//    }
//
//    public void setBlood_group(String blood_group) {
//        this.blood_group = blood_group;
//    }
//
//    public String getSubcaste() {
//        return subcaste;
//    }
//
//    public void setSubcaste(String subcaste) {
//        this.subcaste = subcaste;
//    }
//
//    public String getMarried() {
//        return married;
//    }
//
//    public void setMarried(String married) {
//        this.married = married;
//    }
//
//    public String getSecondment() {
//        return secondment;
//    }
//
//    public void setSecondment(String secondment) {
//        this.secondment = secondment;
//    }
//
//    public String getSecondment_org_id() {
//        return secondment_org_id;
//    }
//
//    public void setSecondment_org_id(String secondment_org_id) {
//        this.secondment_org_id = secondment_org_id;
//    }
//
//    public String getService_initiation_id() {
//        return service_initiation_id;
//    }
//
//    public void setService_initiation_id(String service_initiation_id) {
//        this.service_initiation_id = service_initiation_id;
//    }
//
//    public String getRecord_type() {
//        return record_type;
//    }
//
//    public void setRecord_type(String record_type) {
//        this.record_type = record_type;
//    }
//
//    public String getService_type() {
//        return service_type;
//    }
//
//    public void setService_type(String service_type) {
//        this.service_type = service_type;
//    }
//
//    public String getOrder_no() {
//        return order_no;
//    }
//
//    public void setOrder_no(String order_no) {
//        this.order_no = order_no;
//    }
//
//    public String getOrder_date() {
//        return order_date;
//    }
//
//    public void setOrder_date(String order_date) {
//        this.order_date = order_date;
//    }
//
//    public String getOrder_source() {
//        return order_source;
//    }
//
//    public void setOrder_source(String order_source) {
//        this.order_source = order_source;
//    }
//
//    public String getOrder_sent_on() {
//        return order_sent_on;
//    }
//
//    public void setOrder_sent_on(String order_sent_on) {
//        this.order_sent_on = order_sent_on;
//    }
//
//    public String getOrder_accepted_on() {
//        return order_accepted_on;
//    }
//
//    public void setOrder_accepted_on(String order_accepted_on) {
//        this.order_accepted_on = order_accepted_on;
//    }
//
//    public String getAcknowledge_received_on() {
//        return acknowledge_received_on;
//    }
//
//    public void setAcknowledge_received_on(String acknowledge_received_on) {
//        this.acknowledge_received_on = acknowledge_received_on;
//    }
//
//    public String getDocument_no() {
//        return document_no;
//    }
//
//    public void setDocument_no(String document_no) {
//        this.document_no = document_no;
//    }
//
//    public String getService_initiation_appoint_id() {
//        return service_initiation_appoint_id;
//    }
//
//    public void setService_initiation_appoint_id(String service_initiation_appoint_id) {
//        this.service_initiation_appoint_id = service_initiation_appoint_id;
//    }
//
//    public String getOrder_reason() {
//        return order_reason;
//    }
//
//    public void setOrder_reason(String order_reason) {
//        this.order_reason = order_reason;
//    }
//
//    public String getOrder_reason_type() {
//        return order_reason_type;
//    }
//
//    public void setOrder_reason_type(String order_reason_type) {
//        this.order_reason_type = order_reason_type;
//    }
//
//    public String getService_effected_on() {
//        return service_effected_on;
//    }
//
//    public void setService_effected_on(String service_effected_on) {
//        this.service_effected_on = service_effected_on;
//    }
//
//    public String getService_end_on() {
//        return service_end_on;
//    }
//
//    public void setService_end_on(String service_end_on) {
//        this.service_end_on = service_end_on;
//    }
//
//    public String getService_status() {
//        return service_status;
//    }
//
//    public void setService_status(String service_status) {
//        this.service_status = service_status;
//    }
//
//    public String getApproval_process_id() {
//        return approval_process_id;
//    }
//
//    public void setApproval_process_id(String approval_process_id) {
//        this.approval_process_id = approval_process_id;
//    }
//
//    public String getApproval_by() {
//        return approval_by;
//    }
//
//    public void setApproval_by(String approval_by) {
//        this.approval_by = approval_by;
//    }
//
//    public String getApproval_on() {
//        return approval_on;
//    }
//
//    public void setApproval_on(String approval_on) {
//        this.approval_on = approval_on;
//    }
//
//    public String getApproval_reference() {
//        return approval_reference;
//    }
//
//    public void setApproval_reference(String approval_reference) {
//        this.approval_reference = approval_reference;
//    }
//
//    public String getWef() {
//        return wef;
//    }
//
//    public void setWef(String wef) {
//        this.wef = wef;
//    }
//
//    public String getWet() {
//        return wet;
//    }
//
//    public void setWet(String wet) {
//        this.wet = wet;
//    }
//
//    public String getAppoint_id() {
//        return appoint_id;
//    }
//
//    public void setAppoint_id(String appoint_id) {
//        this.appoint_id = appoint_id;
//    }
//
//    public String getAppointment_type() {
//        return appointment_type;
//    }
//
//    public void setAppointment_type(String appointment_type) {
//        this.appointment_type = appointment_type;
//    }
//
//    public String getNature_of_employment() {
//        return nature_of_employment;
//    }
//
//    public void setNature_of_employment(String nature_of_employment) {
//        this.nature_of_employment = nature_of_employment;
//    }
//
//    public String getProposed_joining_date() {
//        return proposed_joining_date;
//    }
//
//    public void setProposed_joining_date(String proposed_joining_date) {
//        this.proposed_joining_date = proposed_joining_date;
//    }
//
//    public String getDate_of_join() {
//        return date_of_join;
//    }
//
//    public void setDate_of_join(String date_of_join) {
//        this.date_of_join = date_of_join;
//    }
//
//    public String getProposed_probation_end_date() {
//        return proposed_probation_end_date;
//    }
//
//    public void setProposed_probation_end_date(String proposed_probation_end_date) {
//        this.proposed_probation_end_date = proposed_probation_end_date;
//    }
//
//    public String getRevised_probation_end_date() {
//        return revised_probation_end_date;
//    }
//
//    public void setRevised_probation_end_date(String revised_probation_end_date) {
//        this.revised_probation_end_date = revised_probation_end_date;
//    }
//
//    public String getProbation_end_date() {
//        return probation_end_date;
//    }
//
//    public void setProbation_end_date(String probation_end_date) {
//        this.probation_end_date = probation_end_date;
//    }
//
//    public String getConfirmation_on() {
//        return confirmation_on;
//    }
//
//    public void setConfirmation_on(String confirmation_on) {
//        this.confirmation_on = confirmation_on;
//    }
//
//    public String getEstimated_retirement_date() {
//        return estimated_retirement_date;
//    }
//
//    public void setEstimated_retirement_date(String estimated_retirement_date) {
//        this.estimated_retirement_date = estimated_retirement_date;
//    }
//
//    public String getRevised_retirement_date() {
//        return revised_retirement_date;
//    }
//
//    public void setRevised_retirement_date(String revised_retirement_date) {
//        this.revised_retirement_date = revised_retirement_date;
//    }
//
//    public String getEstimated_date_of_exit() {
//        return estimated_date_of_exit;
//    }
//
//    public void setEstimated_date_of_exit(String estimated_date_of_exit) {
//        this.estimated_date_of_exit = estimated_date_of_exit;
//    }
//
//    public String getDate_of_exit() {
//        return date_of_exit;
//    }
//
//    public void setDate_of_exit(String date_of_exit) {
//        this.date_of_exit = date_of_exit;
//    }
//
//    public String getExit_type() {
//        return exit_type;
//    }
//
//    public void setExit_type(String exit_type) {
//        this.exit_type = exit_type;
//    }
//
//    public String getExit_reason() {
//        return exit_reason;
//    }
//
//    public void setExit_reason(String exit_reason) {
//        this.exit_reason = exit_reason;
//    }
//
//    public String getJoining_position() {
//        return joining_position;
//    }
//
//    public void setJoining_position(String joining_position) {
//        this.joining_position = joining_position;
//    }
//
//    public String getJoining_entity_id() {
//        return joining_entity_id;
//    }
//
//    public void setJoining_entity_id(String joining_entity_id) {
//        this.joining_entity_id = joining_entity_id;
//    }
//
//    public String getJoining_entity_structure_id() {
//        return joining_entity_structure_id;
//    }
//
//    public void setJoining_entity_structure_id(String joining_entity_structure_id) {
//        this.joining_entity_structure_id = joining_entity_structure_id;
//    }
//
//    public String getJoining_sub_grade() {
//        return joining_sub_grade;
//    }
//
//    public void setJoining_sub_grade(String joining_sub_grade) {
//        this.joining_sub_grade = joining_sub_grade;
//    }
//
//    public String getCurrent_position() {
//        return current_position;
//    }
//
//    public void setCurrent_position(String current_position) {
//        this.current_position = current_position;
//    }
//
//    public String getCurrent_entity_structure_id() {
//        return current_entity_structure_id;
//    }
//
//    public void setCurrent_entity_structure_id(String current_entity_structure_id) {
//        this.current_entity_structure_id = current_entity_structure_id;
//    }
//
//    public String getCurrent_sub_grade() {
//        return current_sub_grade;
//    }
//
//    public void setCurrent_sub_grade(String current_sub_grade) {
//        this.current_sub_grade = current_sub_grade;
//    }
//
//    public String getRecruitment_id() {
//        return recruitment_id;
//    }
//
//    public void setRecruitment_id(String recruitment_id) {
//        this.recruitment_id = recruitment_id;
//    }
//
//    public String getFinal_settlement_status() {
//        return final_settlement_status;
//    }
//
//    public void setFinal_settlement_status(String final_settlement_status) {
//        this.final_settlement_status = final_settlement_status;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public String getReappoint_id() {
//        return reappoint_id;
//    }
//
//    public void setReappoint_id(String reappoint_id) {
//        this.reappoint_id = reappoint_id;
//    }
//
//    public String getService_id() {
//        return service_id;
//    }
//
//    public void setService_id(String service_id) {
//        this.service_id = service_id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone_no() {
//        return phone_no;
//    }
//
//    public void setPhone_no(String phone_no) {
//        this.phone_no = phone_no;
//    }
//
//    public String getNssf() {
//        return nssf;
//    }
//
//    public void setNssf(String nssf) {
//        this.nssf = nssf;
//    }
//
//    public String getNhif() {
//        return nhif;
//    }
//
//    public void setNhif(String nhif) {
//        this.nhif = nhif;
//    }
//
//    public String getKra() {
//        return kra;
//    }
//
//    public void setKra(String kra) {
//        this.kra = kra;
//    }
//
//    public String getGrade() {
//        return grade;
//    }
//
//    public void setGrade(String grade) {
//        this.grade = grade;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }
//}
