
from django.db import models

# Create your models here.

# class Registration(models.Model):
#     Username=models.CharField(max_length=10)
#     Lastname=models.CharField(max_length=10)
#     email_id=models.CharField(null=False)
#     password=models.CharField(max_length=15)
#     phone_no=models.PositiveBigIntegerField(max_length=10)


# class Login(models.Model):
    
   # email_id=models.ForeignKey(Registration,on_delete=models.CASCADE)
#     password=models.CharField(max_length=15)

# class LabourJob(models.Model):

#     email_id=models.CharField(null=False)
#     job_1=models.CharField(max_length=20)
#     job_2=models.CharField(max_length=15)

class Labour(models.Model):
   
    name=models.CharField(max_length=15)
    age=models.PositiveIntegerField()
    height=models.DecimalField(decimal_places=2, max_digits=10)
    contact=models.CharField(max_length=15, default="")
    weight=models.DecimalField(decimal_places=2, max_digits=10)
    work = models.CharField(max_length=10000)
    emailid=models.CharField(max_length=100)
    password=models.CharField(max_length=15)
    gender = models.CharField(max_length=10, default="male")

    def __str__(self):
      return self.name
   
    # email_id=models.ForeignKey(LabourJob,on_delete=models.CASCADE)



class contractor(models.Model):
  name1=models.CharField(max_length=20,default=True)
  contact=models.CharField(max_length=15, default="")
  email_id1=models.CharField(max_length=100)
  password1=models.CharField(max_length=20,null=True)
  company=models.CharField(max_length=30)

  def __str__(self):
    return self.name1

class worker(models.Model):
    age=models.PositiveBigIntegerField()
    height=models.PositiveIntegerField()
    weight=models.PositiveBigIntegerField()
    gender=models.CharField(max_length=6)

class typework(models.Model):
   id=models.IntegerField(primary_key=True)
   work=models.CharField(max_length=50)

class work(models.Model):
  job_desc=models.CharField(max_length=100)
  job_type=models.CharField(max_length=100)
  no_days=models.IntegerField()
  no_employees_hir=models.IntegerField()
  no_employees_required=models.IntegerField()
  salary=models.CharField(max_length=100)
  contractor_email=models.CharField(max_length=100)


class request(models.Model):
  email=models.CharField(max_length=100)
  job_description=models.CharField(max_length=200)
  # status=models.CharField(max_length=200)
  status = models.IntegerField()

class apply(models.Model):
  job_id=models.IntegerField()
  labour_email=models.CharField(max_length=100)
  # status=models.CharField(max_length=100,default=True)
  status = models.IntegerField(default=0)