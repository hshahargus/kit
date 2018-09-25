/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.login.model;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author Harshit
 */
@Entity
@Table(name = "last_access_token")
@Data
public class LastAccessTokenDetailEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "request_token")
    private String requestToken;
    @Column(name = "api_secret")
    private String apiSecret;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "public_token")
    private String publicToken;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "created_on")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdOn;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LastAccessTokenDetailEntity)) {
            return false;
        }
        LastAccessTokenDetailEntity other = (LastAccessTokenDetailEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}
