-- CREATE TABLE
CREATE TABLE Invoice
(
    "id"  serial,
    "name"   text NOT NULL,
    "firm"   text NOT NULL,
    "amount" text NOT NULL,
    CONSTRAINT Overhead_pk PRIMARY KEY ("name")
) WITH (
      OIDS= FALSE
    );
