package com.carbondev.carboncheck.domain.validation

import io.konform.validation.Validation
import io.konform.validation.constraints.pattern

object ValidationRules {
    val email = Validation<String> {
        pattern("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") hint "Please enter a valid email address."
    }

    val password = Validation<String> {
        // At least 8 characters, 1 letter and 1 number
        pattern(Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{8,}$")) hint "Password must be at least 8 characters, with one letter, one number, and one special character."
    }
}