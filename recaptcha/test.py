import requests

resp = requests.post(
    "https://www.google.com/recaptcha/api/siteverify",
    data={
        "secret": "6Le-2MMkAAAAALofmcnS8hQgpf1zViHV1M7C2iVu",
        "response": "03AFY_a8V0KZYDuot_HYZvQdcSRpollyEB81UtU8Uouxp44IsEmc8k8jxpW7QTRykKkzuaTdOClU0xGiqiDzp_RKLDazR_lrg0xHbZtlI7DjkZEy4I62JlZK14cNWVfIOgkvodkXFPd836eOQ3UPbMJc8TRIiCmkKTgJ0qysFuXxisCNJFygUHW1ggnMuTO4XaJPxQe3Lp7R85vu6GMC24tkGC6UfnR3BUO58aqO0h-paUj_HH5NgHRVkL0Rvlf73lPXXmc_W433u6tbJJjldi0Rfpy-Xox-jW-l0UgdgGug7uvjyAeT1JFlQxE4EVKPg2GiNr9CQNBGJPghN97tUK0kMiTaOULJkQkNh1qhZD2I7p8Y7A4ljOYSxek3m2BwCUmoArBUpT8XPB_c94kwzGmUalEmymtvVo76wvFfybCq78k1Sgles1dxGIVRzLsnqiQvl6IY3mB6cmlrc0JFvidW2a7pqfDoufsjXU-p_S8uzgeH-Cr4EPbjSMLee1kDXBqIlCwl2kKQa0KlzLWLaqhv1l1quIsetVC12zwnx7jH8_HSkNjposTo_XW7ftI9hToIgsK07EO6GANBReaqAUQ9pLwOeBTnvgig"
    }
)

print(resp.json())