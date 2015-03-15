package kz.kerey.business.types.tasks

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderColumn

import kz.kerey.business.types.GoodItem
import kz.kerey.business.types.documents.Document
import kz.kerey.business.types.enums.TaskStatus
import kz.kerey.business.user.User
import kz.kerey.flow.Ladder
import kz.kerey.flow.Step

@Entity
class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id

    @Enumerated(EnumType.STRING)
    TaskStatus status

    @ManyToOne
    Ladder ladder

    @ManyToOne
    Step step

    @ManyToOne
    User executor

    @OneToMany
    List<Document> documents

    @OneToMany
    @OrderColumn(name="index_column")
    List<GoodItem> items

	String taskName
	String taskComment
    Date initialDate
    Date finishDate;
    Date deadlineDate
    String barcode

}