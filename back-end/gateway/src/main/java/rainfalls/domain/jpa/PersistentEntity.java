package rainfalls.domain.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class PersistentEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="primary_table_generator")
	@TableGenerator(name="primary_table_generator", table="sequences")
	private Long id;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date updated;
    
    public Long getId() {
		return id;
	}
    
    public Date getCreated() {
		return created;
	}
    
    public Date getUpdated() {
		return updated;
	}
    
    @PrePersist
    protected void onCreate() {
      created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      updated = new Date();
    }
}
