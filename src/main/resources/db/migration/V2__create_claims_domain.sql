create table customers (
    id uuid primary key,
    document_type varchar(20) not null,
    document_number varchar(30) not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    email varchar(160) not null,
    phone varchar(40),
    created_at timestamp not null,
    updated_at timestamp not null,
    constraint uk_customers_document unique (document_type, document_number)
);

create table policies (
    id uuid primary key,
    policy_number varchar(40) not null,
    customer_id uuid not null,
    product_type varchar(80) not null,
    start_date date not null,
    end_date date not null,
    status varchar(20) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    constraint uk_policies_policy_number unique (policy_number),
    constraint fk_policies_customer foreign key (customer_id) references customers (id)
);

create table claims (
    id uuid primary key,
    claim_number varchar(40) not null,
    policy_id uuid not null,
    description varchar(1000) not null,
    status varchar(20) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    constraint uk_claims_claim_number unique (claim_number),
    constraint fk_claims_policy foreign key (policy_id) references policies (id)
);

create table claim_status_history (
    id uuid primary key,
    claim_id uuid not null,
    previous_status varchar(20),
    new_status varchar(20) not null,
    changed_by varchar(120) not null,
    reason varchar(500),
    changed_at timestamp not null,
    constraint fk_claim_status_history_claim foreign key (claim_id) references claims (id)
);

create table audit_logs (
    id uuid primary key,
    actor varchar(120) not null,
    action varchar(80) not null,
    resource_type varchar(80) not null,
    resource_id uuid not null,
    details varchar(1000),
    created_at timestamp not null
);

create index idx_policies_customer_id on policies (customer_id);
create index idx_claims_policy_id on claims (policy_id);
create index idx_claims_status on claims (status);
create index idx_claim_status_history_claim_id on claim_status_history (claim_id);
create index idx_audit_logs_resource on audit_logs (resource_type, resource_id);

