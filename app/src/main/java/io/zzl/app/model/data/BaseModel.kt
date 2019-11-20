package io.zzl.app.model.data

import java.time.Instant

interface BaseModel {

    var creationDate: Instant

    var modificationDate: Instant
}