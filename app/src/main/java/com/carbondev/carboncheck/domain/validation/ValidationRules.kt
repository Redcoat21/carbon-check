package com.carbondev.carboncheck.domain.validation

import io.konform.validation.Validation
import io.konform.validation.constraints.pattern

object ValidationRules {
    val email = Validation<String> {
        pattern("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    }

    val phone = Validation<String> {
        pattern("^\\+?[0-9]{10,15}$") // Generic international phone number pattern
    }

    val password = Validation<String> {
        pattern(Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{8,}$")) // At least 8 characters, 1 letter and 1 number
    }
}