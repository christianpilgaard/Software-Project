package acceptance_tests;

import app.Developer;
import app.Model;
import app.Project;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class AssignProjectmanagerSteps {//Christian
    private Model model;
    private Developer currentCandidate;


    public AssignProjectmanagerSteps(Model model){
        this.model=model;
    }

    @Given("These User IDs are contained in the project {string}")
    public void these_User_IDs_are_contained_in_the_project(String project, List<String> dataTable) {
        model.setCurrentProject(new Project(project, new Developer("FGH")));

        for(String s: dataTable){
            for(Developer d : model.getUserIDs()){
                if (d.getUserId().equals(s)){
                    model.getCurrentProject().addDeveloper(new Developer(s));
                }
            }
        }
    }

    @When("add projectmanager {string}")
    public void add_projectmanager(String id) {
        currentCandidate=new Developer(id);
        for(Developer d: model.getCurrentProject().getDevelopers()){
            if (d.getUserId().equals(id)){
                currentCandidate=d;
            }
        }
        model.assignProjectmanager(currentCandidate,model.getCurrentProject(),model.getCurrentUser());
    }

    @Then("The projectmanager is assigned to the project")
    public void the_projectmanager_is_assigned_to_the_project() {
        assertEquals(currentCandidate,model.getCurrentProject().getProjectmanager());
    }
}
