package com.helixcry.zcc.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = false)
public class Ticket {
	public String url;
    public int id;
    public Object external_id;
    public Via via;
    public Date created_at;
    public Date updated_at;
    public Object type;
    public String subject;
    public String raw_subject;
    public String description;
    public Object priority;
    public String status;
    public Object recipient;
    public long requester_id;
    public long submitter_id;
    public long assignee_id;
    public long organization_id;
    public long group_id;
    public List<Object> collaborator_ids;
    public List<Object> follower_ids;
    public List<Object> email_cc_ids;
    public Object forum_topic_id;
    public Object problem_id;
    public boolean has_incidents;
    public boolean is_public;
    public Object due_at;
    public List<String> tags;
    public List<Object> custom_fields;
    public Object satisfaction_rating;
    public List<Object> sharing_agreement_ids;
    public List<Object> fields;
    public List<Object> followup_ids;
    public long ticket_form_id;
    public long brand_id;
    public boolean allow_channelback;
    public boolean allow_attachments;
}
