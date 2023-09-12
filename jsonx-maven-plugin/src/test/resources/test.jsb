{
  "@ns": "http://www.jsonx.org/binding-0.4.jsd",
  "@schemaLocation": "http://www.jsonx.org/binding-0.4.jsd http://www.jsonx.org/binding.jsd",
  "@schema": {
    "@ns": "http://www.jsonx.org/schema-0.4.jsd",
    "primitiveBoolean": {
      "@": "boolean"
    },
    "nonEmptyString": {
      "@": "string",
      "@pattern": "\\S|\\S.*\\S"
    },
    "currency": {
      "@": "string",
      "@pattern": "AUD|CAD|EUR|GBP|JPY|USD"
    },
    "dateTime": {
      "@": "string",
      "@pattern": "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(.{1,3})?"
    },
    "url": {
      "@": "string",
      "@pattern": "(https?|ftp)://[\\w\\d:#@%/;$()~_?'\\+-=\\\\\\.&]+"
    },
    "xmldsig": {
      "@": "object",
      "@abstract": true,
      "@properties": {
        "xmldsig": {
          "@": "string"
        }
      }
    },
    "pubRsa": {
      "@": "object",
      "@abstract": true,
      "@extends": "xmldsig",
      "@properties": {
        "pub_rsa": {
          "@": "string"
        }
      }
    },
    "signature": {
      "@": "object",
      "@extends": "pubRsa"
    },
    "dsig": {
      "@": "object",
      "@extends": "xmldsig"
    },
    "partialData": {
      "@": "object",
      "@abstract": true,
      "@properties": {
        "1": {
          "@": "string",
          "@pattern": "[\"0-9A-F]*"
        },
        "2": {
          "@": "string",
          "@pattern": "[\\\\0-9A-F]*"
        }
      }
    },
    "message": {
      "@": "object",
      "@properties": {
        "subject": {
          "@": "string"
        },
        "url": {
          "@": "reference",
          "@type": "url"
        },
        "important": {
          "@": "boolean"
        },
        "requiredArray": {
          "@": "array",
          "@elements": [{
              "@": "reference",
              "@nullable": false,
              "@type": "primitiveBoolean"
            }]
        },
        "notRequired": {
          "@": "boolean",
          "@use": "optional"
        },
        "notRequiredArray": {
          "@": "array",
          "@use": "optional",
          "@elements": [{
              "@": "reference",
              "@nullable": false,
              "@type": "primitiveBoolean"
            }]
        },
        "recipients": {
          "@": "array",
          "@elements": [{
              "@": "string",
              "@nullable": false,
              "@pattern": ".+"
            }]
        },
        "emptyarray": {
          "@": "array",
          "@elements": [{
              "@": "string",
              "@nullable": false,
              "@pattern": ".+"
            }]
        },
        "attachment": {
          "@": "array",
          "@elements": [{
              "@": "reference",
              "@nullable": false,
              "@type": "attachment"
            }]
        },
        "signature": {
          "@": "reference",
          "@type": "signature"
        }
      }
    },
    "attachment": {
      "@": "object",
      "@properties": {
        "filename": {
          "@": "string",
          "@nullable": false
        },
        "data": {
          "@": "object",
          "@extends": "partialData",
          "@properties": {
            "c": {
              "@": "string",
              "@pattern": "[0-9A-F]*"
            }
          }
        },
        "serial": {
          "@": "number",
          "@nullable": false
        }
      }
    },
    "links": {
      "@": "object",
      "@properties": {
        "href": {
          "@": "reference",
          "@nullable": false,
          "@type": "url"
        },
        "rel": {
          "@": "reference",
          "@nullable": false,
          "@type": "nonEmptyString"
        },
        "method": {
          "@": "reference",
          "@nullable": false,
          "@type": "nonEmptyString"
        },
        "enc": {
          "@": "string",
          "@use": "optional"
        }
      }
    },
    "payPalEvent": {
      "@": "object",
      "@properties": {
        "id": {
          "@": "string",
          "@nullable": false
        },
        "create_time": {
          "@": "reference",
          "@nullable": false,
          "@type": "dateTime"
        },
        "event_type": {
          "@": "reference",
          "@nullable": false,
          "@type": "nonEmptyString"
        },
        "event_version": {
          "@": "reference",
          "@nullable": false,
          "@type": "nonEmptyString"
        },
        "summary": {
          "@": "reference",
          "@nullable": false,
          "@type": "nonEmptyString"
        },
        "resource_type": {
          "@": "reference",
          "@nullable": false,
          "@type": "nonEmptyString"
        },
        "resource": {
          "@": "object",
          "@properties": {
            "id": {
              "@": "reference",
              "@nullable": false,
              "@type": "nonEmptyString"
            },
            "parent_payment": {
              "@": "reference",
              "@nullable": false,
              "@type": "nonEmptyString"
            },
            "update_time": {
              "@": "reference",
              "@nullable": false,
              "@type": "dateTime"
            },
            "create_time": {
              "@": "reference",
              "@nullable": false,
              "@type": "dateTime"
            },
            "state": {
              "@": "reference",
              "@nullable": false,
              "@type": "nonEmptyString"
            },
            "amount": {
              "@": "object",
              "@nullable": false,
              "@properties": {
                "total": {
                  "@": "reference",
                  "@nullable": false,
                  "@type": "nonEmptyString"
                },
                "currency": {
                  "@": "reference",
                  "@nullable": false,
                  "@type": "currency"
                },
                "details": {
                  "@": "object",
                  "@nullable": false,
                  "@use": "optional",
                  "@properties": {
                    "subtotal": {
                      "@": "reference",
                      "@nullable": false,
                      "@type": "nonEmptyString"
                    }
                  }
                }
              }
            },
            "links": {
              "@": "array",
              "@nullable": false,
              "@elements": [{
                  "@": "reference",
                  "@nullable": false,
                  "@type": "links"
                }]
            },
            "sale_id": {
              "@": "reference",
              "@nullable": false,
              "@type": "nonEmptyString",
              "@use": "optional"
            },
            "payment_mode": {
              "@": "reference",
              "@nullable": false,
              "@type": "nonEmptyString",
              "@use": "optional"
            },
            "protection_eligibility": {
              "@": "reference",
              "@nullable": false,
              "@type": "nonEmptyString",
              "@use": "optional"
            },
            "invoice_number": {
              "@": "string",
              "@nullable": false,
              "@use": "optional"
            },
            "custom": {
              "@": "string",
              "@nullable": false,
              "@use": "optional"
            },
            "refund_to_payer": {
              "@": "object",
              "@nullable": false,
              "@use": "optional",
              "@properties": {
                "value": {
                  "@": "reference",
                  "@nullable": false,
                  "@type": "nonEmptyString"
                },
                "currency": {
                  "@": "reference",
                  "@nullable": false,
                  "@type": "currency"
                }
              }
            },
            "transaction_fee": {
              "@": "object",
              "@nullable": false,
              "@use": "optional",
              "@properties": {
                "value": {
                  "@": "reference",
                  "@nullable": false,
                  "@type": "nonEmptyString"
                },
                "currency": {
                  "@": "reference",
                  "@nullable": false,
                  "@type": "currency"
                }
              }
            }
          }
        },
        "links": {
          "@": "array",
          "@nullable": false,
          "@elements": [{
              "@": "reference",
              "@nullable": false,
              "@type": "links"
            }]
        }
      }
    },
    "data": {
      "@": "object",
      "@properties": {
        "id": {
          "@": "string",
          "@nullable": false
        },
        "url": {
          "@": "string",
          "@nullable": false
        }
      }
    },
    "giphy": {
      "@": "object",
      "@properties": {
        "data": {
          "@": "array",
          "@elements": [{
              "@": "reference",
              "@nullable": false,
              "@type": "data"
            }]
        }
      }
    }
  },
  "primitiveBoolean": {
    "@": "boolean",
    "java": {
      "@type": "boolean"
    }
  },
  "url": {
    "@": "string",
    "java": {
      "@decode": "java.net.URL.<init>",
      "@type": "java.net.URL"
    }
  },
  "partialData.1": {
    "@": "string",
    "java": {
      "@field": "a"
    }
  },
  "partialData.2": {
    "@": "string",
    "java": {
      "@field": "b"
    }
  },
  "message.url": {
    "@": "reference",
    "java": {
      "@field": "location"
    }
  },
  "links.href": {
    "@": "reference",
    "java": {
      "@field": "location"
    }
  }
}