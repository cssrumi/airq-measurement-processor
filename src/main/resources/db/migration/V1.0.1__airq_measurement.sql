CREATE TABLE AIRQ_MEASUREMENT
(
    id          BIGSERIAL PRIMARY KEY,
    timestamp   TIMESTAMPTZ,
    temperature DOUBLE PRECISION,
    humidity    DOUBLE PRECISION,
    pm10        DOUBLE PRECISION,
    pm25        DOUBLE PRECISION,
    stationId   VARCHAR(50),
    location    VARCHAR(100)
)
