from django.contrib import admin
from . import models
# Register your models here.
admin.site.register(models.Labour)
admin.site.register(models.contractor)
admin.site.register(models.worker)
admin.site.register(models.typework)
admin.site.register(models.work)
admin.site.register(models.request)
admin.site.register(models.apply)