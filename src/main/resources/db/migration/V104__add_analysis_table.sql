CREATE TABLE analyzed_samples (
  event_id           INTEGER   NOT NULL REFERENCES stubhub_event (event_id),
  as_of_date         TIMESTAMP NOT NULL,
  count              INTEGER   NOT NULL,
  average            FLOAT     NOT NULL,
  minimum            FLOAT     NOT NULL,
  maximum            FLOAT     NOT NULL,
  standard_deviation FLOAT     NOT NULL
)