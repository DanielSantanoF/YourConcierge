package com.dsantano.dam.yourconcierge.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Ticket(
        @Column(name = "incidence")
        var incidence: String,
        @Column(name = "description")
        var description: String,
        @Column(name = "urgent")
        var urgent: Boolean,
        @Column(name = "open")
        var open: Boolean? = null,
        @JsonBackReference
        @ManyToOne
        var user: User? = null,
        @CreatedDate
        @Column(name = "created_date", nullable = false, updatable = false)
        var createdAt: LocalDate? = null,
        @LastModifiedDate
        @Column(name = "last_modified_date", nullable = false)
        var lastUpdate: LocalDate? = null,
        @Id @GeneratedValue val id: UUID? = null

)