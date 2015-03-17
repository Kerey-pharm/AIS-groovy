package kz.kerey.business.wrappers

class GoodTypeWrapper implements Serializable {

	Long id
	String name

	@Override
	String toString() {
		(id!=null && id!=0) ? name : "ПУСТО"
	}
	
}
