const token="";
const baseURL="https://api.github.com";
const owner="sudiptapramanik209";


var name="Cypress_API_testing";
var description="This is your first repo!";
var updated="Cypress_API_testing_updated";
var shs;
var organization="sudipta1234-pramanik";
var Forked_name="Forked_org";
var id;

describe('Github_api_testing', () => {
    it('1 CREATE A REPOSITORY FOR A AUTHENTICATED USER', () => {
        cy.request({
            method:"POST",
            url:`${baseURL}/user/repos`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            body:{
                "name":name,
                "description":description,
             }
            
        }).then((response)=>{
        
            expect(response.status).to.eql(201);
            cy.log(JSON.stringify(response.body.name))    
        })
    });
    it('2.UPDATE A REPOSITORY', () => {
        cy.request({
            method:"PATCH",
            url:`${baseURL}/repos/${owner}/${name}`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            body:{
                "name":updated,
                "description":description,
             }
            
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));    
        })
        
    });


    it('4. GET A REPOSITORY', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            
        }).then((response)=>{
        
            expect(response.status).to.eql(200);  
            cy.log(JSON.stringify(response)); 
        })
        
    });

    it('CREATE FILE CONTENT', () => {
        cy.request({
            method:"PUT",
            url:`${baseURL}/repos/${owner}/${updated}/contents/java.js`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            body:{
                "message": "create file",
                "content": "SGkgCgpZb3VyIEZpbGUgaXMgU3VjY2Vzc2Z1bGx5IENyZWF0ZWQ="
             }
            
        }).then((response)=>{
            expect(response.status).to.eql(201);
            //cy.log(JSON.stringify(response.body.name))
            shs=response.body.content.sha
            cy.log(JSON.stringify(shs));
            cy.log(JSON.stringify(response));
        })
        
    });

    it('CREATE A FORK', () => {
        cy.request({
            method:"POST",
            url:`${baseURL}/repos/${owner}/${updated}/forks`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            body:{
                "organization":organization,
                "name":Forked_name,
                "default_branch_only":true
             }
            
        }).then((response)=>{
        
            expect(response.status).to.eql(202);
            //cy.log(JSON.stringify(response.body.name))
            cy.log(JSON.stringify(response));
        })
        
    });

    it('6. LIST FORKS', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}/forks`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));
        })

        
    });

    it('7. LIST REPOSITORIES FOR A USER', () => {

        cy.request({
            method:"GET",
            url:`${baseURL}/users/${owner}/repos`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));
        })
        
    });
    it('8. LIST REPOSITORY LANGUAGE', () => {

        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}/languages`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));
        })
        
    });

    it('9. LIST PUBLIC REPOSITORIES', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repositories`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));
        })
    });
    it('12.LIST REPOSITORY TAGS', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}/tags`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));
        })
        
    });

    it('13.CREATE AN AUTOLINK REFERENCE FOR A REPOSITORY', () => {

        cy.request({
            method:"POST",
            url:`${baseURL}/repos/${owner}/${updated}/autolinks`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            body:{
                    "key_prefix": "API",
                    "url_template": "https://example.com/TICKET?query=<num>"
             }
            
        }).then((response)=>{
        
            expect(response.status).to.eql(201);
            //cy.log(JSON.stringify(response.body.name))
            id=JSON.stringify(response.body.id)
            cy.log(JSON.stringify(response));
            //cy.log(id);

        })
        
    });

    it('15.GET AN AUTOLINK REFERENCE FOR A REPOSITORY', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}/autolinks/${id}`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));

        })
        
    });

    it('17.GET A REPOSITORY', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));

        })
        
    });

    it('18.REPLACE ALL REPOSITORY TOPICS', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}/topics`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            body:{
                "names": [
                    "java",
                    "html5",
                    "javascript"
                ]
            }
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));

        })
        
    });

    it('GET ALL REPOSITORY TOPICS', () => {
        cy.request({
            method:"GET",
            url:`${baseURL}/repos/${owner}/${updated}/topics`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            }
        }).then((response)=>{
        
            expect(response.status).to.eql(200);
            cy.log(JSON.stringify(response));

        })
        
        
    });

    it('DELETE FORKS', () => {
        cy.wait(2000)
        cy.request({
            method:"DELETE",
            url:`${baseURL}/repos/${organization}/${Forked_name}`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            
        }).then((response)=>{
        
            expect(response.status).to.eql(204); 
            cy.log(JSON.stringify(response));  
        })
        
    });
    
    it('DELETE A FILE', () => {
        cy.request({
            method:"DELETE",
            url:`${baseURL}/repos/${owner}/${updated}/contents/java.js`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            body:{
                    "message":"Delete Successfully",
                    "sha":shs
            }
            
        }).then((response)=>{
        
            expect(response.status).to.eql(200); 
            cy.log(JSON.stringify(response));  
        })
        
    });
    it('DELETE FROM AN AUTOLINK REFERENCE FOR A REPOSITORY', () => {
        cy.request({
            method:"DELETE",
            url:`${baseURL}/repos/${owner}/${updated}/autolinks/${id}`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            }
            
        }).then((response)=>{
        
            expect(response.status).to.eql(204); 
            cy.log(JSON.stringify(response));  
        })

        
    });
    it('3. DELETE A REPOSITORY', () => {
        cy.request({
            method:"DELETE",
            url:`${baseURL}/repos/${owner}/${updated}`,
            headers:{
                Authorization: `Bearer ${token}`,
                accept:"application/json"
            },
            
        }).then((response)=>{
        
            expect(response.status).to.eql(204); 
            cy.log(JSON.stringify(response));  
        })
        
    });

    
});