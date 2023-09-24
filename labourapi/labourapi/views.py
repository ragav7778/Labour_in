from rest_framework.decorators import api_view
from  rest_framework.response import Response
from . import models




@api_view(['POST'])
def labour_registration(request):
   name=request.POST.get('name')
   age=request.POST.get('age')
   contact = request.POST.get('contact')
   gender = request.POST.get('gender')
   height=request.POST.get('height')
   weight=request.POST.get('weight')
   email_id=request.POST.get('email')
   password=request.POST.get('password')
   gender = request.POST.get("gender")
   x=models.Labour.objects.create(name=name,age=age,height=height,contact=contact,weight=weight,emailid=email_id,password=password, gender=gender)
   print(x)
   return Response({'message':"registration succcesful"})

@api_view(['POST'])
def contractor_registration(request):
   name=request.POST.get('name')
   contact = request.POST.get('contact')
   company = request.POST.get('company')
   email_id=request.POST.get('email')
   password=request.POST.get('password')
   x=models.contractor.objects.create(name1=name,contact=contact, email_id1=email_id,password1=password, company=company)
   print(x)
   return Response({'message':"registration succcesful"})


@api_view(['POST'])
def labour_login(request):
   email_id=request.POST.get('id')
   password=request.POST.get('password')
   x=models.Labour.objects.filter(emailid=email_id,password=password)
   print(x)
   if x:
      print(1)
      return Response({"message":1})
   else:
      print(0)
      return Response({"message":0})


@api_view(['POST'])
def contract_login(request):
  password=request.POST.get('password')
  email=request.POST.get('id')

  x=models.contractor.objects.filter(email_id1=email,password1=password)
  print(x)
  if x:
     print(1)
     return Response({"message":1})
  else:
    print(0)
    return Response({"message":0})


@api_view(['Get'])
def labour_profile(request,id):
   x=models.Labour.objects.filter(emailid=id)
   print(x)
   if x:
      return Response({"name":x[0].name,"age":x[0].age,"weight":x[0].weight,"mailid":x[0].emailid,"height":x[0].height,"contact":x[0].contact,
       "gender":x[0].gender})
   return Response({"name":x[0].name,"age":x[0].age,"weight":x[0].weight,"mailid":x[0].emailid,"height":x[0].height,"contact":x[0].contact,
       "gender":x[0].gender})


@api_view(['Get'])
def contracter_profile(request,id):
   x=models.contractor.objects.filter(email_id1=id)
   if x:
      return Response({"name1":x[0].name1,"contact":x[0].contact,'mailid':x[0].email_id1,'company':x[0].company})

   

@api_view(['POST'])
def add_work(request):
   job_desc=request.POST.get('job_desc')
   job_type=request.POST.get('work')
   no_days=request.POST.get('noofdays')
   no_employees_hir=0
   no_employees_required=request.POST.get('noofemp')
   salary=request.POST.get('salary')
   contractor_email=request.POST.get('id')
   models.work.objects.create(job_desc=job_desc,job_type=job_type,no_days=no_days,no_employees_hir=no_employees_hir,
   no_employees_required=no_employees_required,salary=salary,contractor_email=contractor_email)
   return Response({'message':1})

@api_view(['Get'])
def get_typeofwork(request):
   x=models.typework.objects.all()
   lst=[]
   for i in x:
      lst.append({"work":i.work})
   return Response(lst)

# @api_view(['Get'])
# def contracter_delete_work(request,work_id,id):

# 	x=models.work.objects.filter(contractor_email=work_id ,id=id).delete()
# 	if x[0]:
# 	   return Response({"message":1})

@api_view(['Get'])
def contractor_see_work(request, id):
    x=models.work.objects.filter(contractor_email=id)
    lst=[]
    for i in x:
        lst.append({"job_type":i.job_type, "job_desc":i.job_desc, "noofdays":i.no_days, "noofemp":i.no_employees_required, 
        "noofemphired":i.no_employees_hir, "salary":i.salary, "work_id":i.id})
    return Response(lst)

@api_view(['Get'])
def contractor_delete_work(request, id , work_id):
    x=models.work.objects.filter(contractor_email=id ,id=work_id).delete()
    if x:
        return Response({"message":1})
    return Response({"message":0})


@api_view(['Get'])
def labour_apply(request, id, work_id):
    x=models.apply.objects.create(job_id=work_id, labour_email=id,status=-1)
    return Response({"message":1})

@api_view(["Get"])
def labour_search_work(request,id):
    x=models.apply.objects.filter(labour_email=id)
    lst=[]
    for i in x:
        lst.append(i.job_id)
    y=models.work.objects.all()
    lst1=[]
    for i in y:
        if i.id not in lst:
            lst1.append({"job_type":i.job_type, "job_desc":i.job_desc, "noofdays":i.no_days, "noofemp":i.no_employees_required, 
            "noofemphired":i.no_employees_hir, "salary":i.salary, "work_id":i.id})
    return Response(lst1)

@api_view(['Get'])
def contractor_request(request, id):
    x=models.work.objects.filter(contractor_email=id)
    lst=[]
    lst1=[]
    for i in x:
        lst.append({"job_type":i.job_type, "job_desc":i.job_desc, "noofdays":i.no_days, "noofemp":i.no_employees_required, 
            "noofemphired":i.no_employees_hir, "salary":i.salary, "request_id":i.id})
    for i in lst:
        y=models.apply.objects.filter(job_id=i["request_id"])
        for j in y:
            if j.status==-1:
                z=models.Labour.objects.filter(emailid=j.labour_email)
                temp=i
                temp["name"]=z[0].name
                temp["age"]=z[0].age
                temp["weight"]=z[0].weight
                temp["height"]=z[0].height
                temp["labour_email"]=j.labour_email
                lst1.append(temp)
    return Response(lst1)

@api_view(['Get'])
def contractor_accept_request(request, id, request_id, accept):
    x=models.apply.objects.get(job_id=request_id, labour_email=id)
    x.status=accept
    x.save()
    return Response()

@api_view(['Get'])
def labour_remove(request, id, work_id):
    models.apply.objects.get(labour_email=id, job_id=work_id).delete()
    return Response()

@api_view(['Get'])
def labour_applied(request, id):
    x=models.apply.objects.filter(labour_email=id)
    lst=[]
    for i in x:
        if i.status==-1:
            y=models.work.objects.get(id=i.job_id)
            lst.append({"job_type":y.job_type, "job_desc":y.job_desc, "noofdays":y.no_days, "noofemp":y.no_employees_required, 
            "noofemphired":y.no_employees_hir, "salary":y.salary, "work_id":y.id})
    return Response(lst)
#@api_view(['Get'])
#def work_details(request,contract_email):
 #  x=models.work.objects.filter(contractor_email=contract_email)
 #  y=[{'job_desc':x[0].job_desc},{'job_type':x[0].job_type},{'no_days':x[0].no_days},{'no_employees':x[0].no_employees_hir},{'no_employees_required':x[0].no_employees_required},{'salary':x[0].}]



