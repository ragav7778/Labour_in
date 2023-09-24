
from labourapi import views
from django.urls import path
urlpatterns = [
    path('labour_login',  views.labour_login),
    path('contractor_login',views.contract_login ),
    path("contractor_register", views.contractor_registration),
    path("labourer_register", views.labour_registration),
    path('labour_profile/<id>',views.labour_profile),
    path('contractor_profile/<id>',views.contracter_profile),
    path("get_typesofwork", views.get_typeofwork),
    path("add_work", views.add_work),
    path("contractor_see_work/<id>", views.contractor_see_work),
    path("contractor_delete_work/<id>/<work_id>",views.contractor_delete_work ),
    path("labour_apply_work/<id>/<work_id>", views.labour_apply),
    path("labour_search_work/<id>", views.labour_search_work),
    path("contractor_see_request/<id>", views.contractor_request),
    path("contractor_accept_request/<id>/<request_id>/<accept>", views.contractor_accept_request),
    path("labour_remove_applied/<id>/<work_id>", views.labour_remove),
    path("labour_applied_jobs/<id>", views.labour_applied), 

  
]