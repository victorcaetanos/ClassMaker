package controller.menu;

import controller.classroom.ClassroomController;
import controller.discipline.DisciplineController;
import controller.myClass.MyClassController;
import controller.professor.ProfessorController;
import controller.student.StudentController;
import controller.students_class.StudentClassController;
import model.classroom.repository.ClassroomRepo;
import model.discipline.repository.DisciplineRepo;
import model.myClass.repository.MyClassRepo;
import model.professor.repository.ProfessorRepo;
import model.student.repository.StudentRepo;
import model.student_class.repository.StudentClassRepo;
import view.classroom.ClassroomView;
import view.discipline.DisciplineView;
import view.menu.IMenuView;
import view.myClass.MyClassView;
import view.professor.ProfessorView;
import view.student.StudentView;
import view.student_class.StudentClassView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuController {
    private final IMenuView view;

    public MenuController(IMenuView view) {
        this.view = view;
        initComponents();
    }

    private void initComponents() {
        setButtonProfessorListener();
        setButtonClassroomListener();
        setButtonClassListener();
        setButtonDisciplineListener();
        setButtonStudentsListener();
        setButtonStudentsClassesListener();
    }

    private void setButtonProfessorListener() {
        view.addProfessorButtonActionListener(e -> {
            ProfessorController professorController = new ProfessorController(new ProfessorView(), new ProfessorRepo());
            view.setProfessorsButtonEnabled(false);
            ((ProfessorView) professorController.getView()).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    view.setProfessorsButtonEnabled(true);
                }
            });
        });
    }

    private void setButtonClassroomListener() {
        view.addClassroomsButtonActionListener(e -> {
            ClassroomController classroomController = new ClassroomController(new ClassroomView(), new ClassroomRepo());
            view.setClassroomsButtonEnabled(false);
            ((ClassroomView) classroomController.getView()).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    view.setClassroomsButtonEnabled(true);
                }
            });
        });
    }

    private void setButtonClassListener() {
        view.addClassesButtonActionListener(e -> {
            MyClassController classController = new MyClassController(new MyClassView(), new MyClassRepo());
            view.setClassesButtonEnabled(false);
            ((MyClassView) classController.getView()).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    view.setClassesButtonEnabled(true);
                }
            });
        });
    }

    private void setButtonDisciplineListener() {
        view.addDisciplinesButtonActionListener(e -> {
            DisciplineController disciplineController = new DisciplineController(new DisciplineView(), new DisciplineRepo());
            view.setDisciplinesButtonEnabled(false);
            ((DisciplineView) disciplineController.getView()).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    view.setDisciplinesButtonEnabled(true);
                }
            });
        });
    }

    private void setButtonStudentsListener() {
        view.addStudentsButtonActionListener(e -> {
            StudentController studentController = new StudentController(new StudentView(), new StudentRepo());
            view.setStudentsButtonEnabled(false);
            ((StudentView) studentController.getView()).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    view.setStudentsButtonEnabled(true);
                }
            });
        });
    }

    private void setButtonStudentsClassesListener() {
        view.addStudentsClassesButtonActionListener(e -> {
            StudentClassController studentClassController = new StudentClassController(new StudentClassView(), new StudentClassRepo());
            view.setStudentsClassesButtonEnabled(false);
            ((StudentClassView) studentClassController.getView()).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    view.setStudentsClassesButtonEnabled(true);
                }
            });
        });
    }
}
