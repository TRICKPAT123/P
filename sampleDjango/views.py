from django.http import HttpResponse, JsonResponse

def home(request):
    return HttpResponse("Hello, World!new me ")

def get_data(request):
    data = {
        "message": "Hello from Django!",
        "status": "success"
    }
    return JsonResponse(data)