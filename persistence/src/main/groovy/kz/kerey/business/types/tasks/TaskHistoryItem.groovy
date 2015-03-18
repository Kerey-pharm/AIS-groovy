package kz.kerey.business.types.tasks

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

import kz.kerey.business.types.documents.Document
import kz.kerey.business.user.User
import kz.kerey.flow.Ladder
import kz.kerey.flow.Step

@Entity
class TaskHistoryItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id

    @ManyToOne
    User userFinished

    @ManyToOne
    Step step

    @ManyToOne
    Ladder ladder

    @OneToMany
    List<Document> documents

    @ManyToOne
	Task task

	Date dateStarted
	Date dateFinished
	String comment
	

}