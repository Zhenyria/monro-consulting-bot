ALTER TABLE IF EXISTS monro.shoes
    ADD COLUMN IF NOT EXISTS id INTEGER DEFAULT nextval('monro.global_seq') NOT NULL;

ALTER TABLE IF EXISTS monro.shoes_scales
    DROP CONSTRAINT IF EXISTS fk__shoes_scales__shoes_vendor_code__vendor_code,
    ADD COLUMN IF NOT EXISTS shoes_id INTEGER NULL;

UPDATE monro.shoes_scales AS ss
SET shoes_id = s.id
FROM monro.shoes AS s
WHERE s.vendor_code = ss.shoes_vendor_code;

ALTER TABLE IF EXISTS monro.shoes_scales
    DROP COLUMN IF EXISTS shoes_vendor_code,
    ALTER COLUMN shoes_id SET NOT NULL;

ALTER TABLE IF EXISTS monro.customer_shoes_wish
    DROP CONSTRAINT IF EXISTS fk__customer_shoes_wish__shoes__vendor_code__vendor_code,
    ADD COLUMN IF NOT EXISTS shoes_id INTEGER NULL;

UPDATE monro.customer_shoes_wish AS csw
SET shoes_id = s.id
FROM monro.shoes AS s
WHERE s.vendor_code = csw.vendor_code;

ALTER TABLE IF EXISTS monro.customer_shoes_wish
    DROP COLUMN IF EXISTS vendor_code,
    ALTER COLUMN shoes_id SET NOT NULL;

ALTER TABLE IF EXISTS monro.shoes
    DROP CONSTRAINT IF EXISTS shoes_pkey,
    ADD PRIMARY KEY (id);

ALTER TABLE IF EXISTS monro.shoes_scales
    ADD CONSTRAINT fk__shoes_scales__shoes__shoes_id__id FOREIGN KEY (shoes_id) REFERENCES monro.shoes (id);

ALTER TABLE IF EXISTS monro.customer_shoes_wish
    ADD CONSTRAINT fk__customer_shoes_wish__shoes__shoes_id__id FOREIGN KEY (shoes_id) REFERENCES monro.shoes (id);
