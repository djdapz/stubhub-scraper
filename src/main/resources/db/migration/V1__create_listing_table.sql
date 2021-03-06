CREATE TABLE stubhubListing (
  listing_id             INTEGER          NOT NULL,
  event_id               INTEGER          NOT NULL,
  as_of_date             TIMESTAMP             NOT NULL,
  current_price_amount   DOUBLE PRECISION NOT NULL,
  current_price_currency VARCHAR(64)      NOT NULL,
  listing_price_amount   DOUBLE PRECISION NOT NULL,
  listing_price_currency VARCHAR(64)      NOT NULL,
  sectionId              INTEGER          NOT NULL,
  quantity               INTEGER          NOT NULL,
  zoneId                 INTEGER          NOT NULL,
  isGA                   INTEGER          NOT NULL,
  score                  INTEGER          NOT NULL,
  row                    VARCHAR(64)      NOT NULL,
  sellerSectionName      VARCHAR(64)      NOT NULL,
  sectionName            VARCHAR(64)      NOT NULL,
  seatNumbers            VARCHAR(64),
  zoneName               VARCHAR(64),
  splitOption            VARCHAR(64),
  ticketSplit            VARCHAR(64),
  dirtyTicketInd         BOOLEAN,
  PRIMARY KEY (listing_id, event_id, as_of_date)
)