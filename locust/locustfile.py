from locust import HttpUser, task, between
import random
import string


def generate_random_string(length: int = 8):
    return ''.join(random.choices(string.ascii_letters, k=length))


class SpringPetClinicLoadTest(HttpUser):
    wait_time = between(0, 1)

    @task(1)
    def get_owners(self):
        # Sending GET request to list owners
        response = self.client.get("/petclinic/api/owners")
        if response.status_code == 200:
            print(f"Get all ownerss")
        else:
            print(f"Failed to list Owners: {response.status_code} - {response.text}")

    @task(2)
    def create_owner(self):
        # Step 1: Create an Owner
        owner_payload = {
            "firstName": generate_random_string(),
            "lastName": generate_random_string(),
            "address": generate_random_string(25),
            "city": generate_random_string(5),
            "telephone": "123456789"
        }
        
        # Sending POST request to create the owner
        response = self.client.post("/petclinic/api/owners", json=owner_payload)        
            
        # Check if pet creation is successful
        if response.status_code == 201:
            owner_data = response.json()
            owner_id = owner_data.get('id')
            print(f"Owner created with id: {owner_id}")
        else:
            print(f"Failed to create Owner: {response.status_code} - {response.text}")
