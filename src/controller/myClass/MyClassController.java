package controller.myClass;

import exceptions.ValidationException;
import model.classroom.dto.ClassroomDTO;
import model.discipline.dto.DisciplineDTO;
import model.myClass.dto.MyClassDTO;
import model.myClass.entity.MyClass;
import model.professor.dto.ProfessorDTO;
import service.myClass.IMyClassService;
import view.myClass.IMyClassView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyClassController {
    private final IMyClassView view;
    private final IMyClassService service;

    public MyClassController(IMyClassView view, IMyClassService service) {
        this.view = view;
        this.service = service;
        initComponents();
    }

    private void initComponents() {
        addInsertMyClassButtonListener();
        addUpdateMyClassButtonListener();
        addDeactivateMyClassButtonListener();
        addReactivateMyClassButtonListener();
        addDoneButtonListener();
        addInactivesCheckBoxListener();
        addTableListener();
        setSearchFieldListener();
        setSearchButtonListener();
        view.populateProfessorComboBox(mapEntityToDTO(service.getAllActiveProfessorsList(), ProfessorDTO.class));
        view.populateDisciplineComboBox(mapEntityToDTO(service.getAllActiveDisciplinesList(), DisciplineDTO.class));
        view.populateClassroomComboBox(mapEntityToDTO(service.getAllActiveClassroomsList(), ClassroomDTO.class));
        view.setTableMyClassModel(getAllMyClassesDTO());
    }

    private void addInsertMyClassButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String professorId = view.getComboBoxProfessorId();
            String disciplineId = view.getComboBoxDisciplineId();
            String classroomId = view.getComboBoxClassroomId();
            String weekDay = view.getWeekDayText();
            String startTime = view.getStartTimeText();
            String finishTime = view.getFinishTimeText();
            String semester = view.getSemesterText();

            try {

                if (!service.insertMyClass(professorId, disciplineId, classroomId,weekDay, startTime, finishTime, semester)) {
                    view.showErrorMessage("Falha ao inserir turma!");
                    return;
                }
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            view.clearAllFields();
            view.setTableMyClassModel(getAllMyClassesDTO());
        });
    }

    private void addUpdateMyClassButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String myClassId = view.getIdText();
            String professorId = view.getComboBoxProfessorId();
            String disciplineId = view.getComboBoxDisciplineId();
            String classroomId = view.getComboBoxClassroomId();
            String weekDay = view.getWeekDayText();
            String startTime = view.getStartTimeText();
            String finishTime = view.getFinishTimeText();
            String semester = view.getSemesterText();

            try {
                if (!service.updateMyClass(myClassId, professorId, disciplineId, classroomId,weekDay, startTime, finishTime, semester)) {
                    view.showErrorMessage("Falha ao atualizar turma!");
                    return;
                }
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableMyClassModel(getAllMyClassesDTO());
        });
    }

    private void addDeactivateMyClassButtonListener() {
        view.addButtonDeleteActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            String myClassId = view.getIdText();

            try {
                if (!service.deleteMyClass(myClassId)) {
                    view.showErrorMessage("Falha ao excluir turma!");
                    return;
                }
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableMyClassModel(getAllMyClassesDTO());
        });
    }

    private void addReactivateMyClassButtonListener() {
        view.addButtonReactivateActionListener(e -> {

            if (!view.confirmReactivation()) {
                return;
            }

            String myClassId = view.getIdText();

            try {
                if (!service.activateMyClass(myClassId)) {
                    view.showErrorMessage("Falha ao ativar turma!");
                    return;
                }
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableMyClassModel(getAllMyClassesDTO());
        });
    }

    private void addTableListener() {
        view.addTableMyClassListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedMyClassRowIndex();
                if (selectedRow >= 0) {
                    String myClassId = view.getMyClassIdAt(selectedRow);

                    MyClassDTO myClass = getMyClassDTO(myClassId);
                    if (myClass == null) {
                        return;
                    }

                    if (!view.isButtonDoneEnabled()) {
                        view.switchButtons(true);
                        view.setButtonReactivate(false);
                    }


                    List<ProfessorDTO> professors = mapEntityToDTO(service.getAllActiveProfessorsList(), ProfessorDTO.class);
                    List<DisciplineDTO> disciplines = mapEntityToDTO(service.getAllActiveDisciplinesList(), DisciplineDTO.class);
                    List<ClassroomDTO> classrooms = mapEntityToDTO(service.getAllActiveClassroomsList(), ClassroomDTO.class);
                    for (ProfessorDTO professor : professors) {
                        if (Objects.equals(professor.getId(), myClass.getProfessorId())) {
                            view.setProfessorComboBox(professor);
                            break;
                        }
                    }
                    for (DisciplineDTO discipline : disciplines) {
                        if (Objects.equals(discipline.getId(), myClass.getDisciplineId())) {
                            view.setDisciplineComboBox(discipline);
                            break;
                        }
                    }
                    for (ClassroomDTO classroom : classrooms) {
                        if (Objects.equals(classroom.getId(), myClass.getClassroomId())) {
                            view.setClassroomComboBox(classroom);
                            break;
                        }
                    }


                    if (view.isCheckBoxInactivesSelected()) {
                        view.setButtonReactivate(true);
                    }

                    view.setFieldID(myClassId);
                    view.setFieldTexts(myClass);
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableMyClassModel(getAllMyClassesDTO());
        });
    }

    private void addInactivesCheckBoxListener() {
        view.addCheckBoxInactivesActionListener(e -> searchWithFilter());
    }

    private void setSearchFieldListener() {
        view.addSearchFieldActionListener(e -> searchWithFilter());
    }

    private void setSearchButtonListener() {
        view.addSearchButtonActionListener(e -> searchWithFilter());
    }

    private void searchWithFilter() {
        view.setTableMyClassModel(getAllMyClassesDTO());
    }

    private MyClassDTO getMyClassDTO(final String id) {
        MyClassDTO myClass;

        try {
            myClass = mapEntityToDTO(service.getMyClass(id, view.isCheckBoxInactivesSelected()));
        } catch (ValidationException e) {
            view.showErrorMessage(e.getMessage());
            return null;
        }

        if (myClass == null) {
            view.showErrorMessage("Falha ao listar turma!");
            return null;
        }
        return myClass;
    }

    public IMyClassView getView() {
        return view;
    }

    public List<MyClassDTO> getAllMyClassesDTO() {
        List<MyClassDTO> myClasses = mapEntityToDTO(service.getMyClassList(view.getSearchBoxText(), view.isCheckBoxInactivesSelected()));

        if (myClasses == null) {
            view.showErrorMessage("Falha ao listar turmas!");
        }
        return myClasses;
    }

    private MyClassDTO mapEntityToDTO(MyClass myClass) {
        MyClassDTO myClassDTO = new MyClassDTO();
        myClassDTO.setId(myClass.getId() + "");
        myClassDTO.setProfessorName(myClass.getProfessor().getName());
        myClassDTO.setProfessorId(myClass.getProfessor().getId() + "");
        myClassDTO.setDisciplineName(myClass.getDiscipline().getName());
        myClassDTO.setDisciplineId(myClass.getDiscipline().getId() + "");
        myClassDTO.setClassroomName(myClass.getClassroom().getName());
        myClassDTO.setClassroomId(myClass.getClassroom().getId() + "");
        myClassDTO.setWeekDay(myClass.getWeeDay());
        myClassDTO.setStartTime(myClass.getStartTime());
        myClassDTO.setFinishTime(myClass.getFinishTime());
        myClassDTO.setSemester(myClass.getSemester());
        return myClassDTO;
    }

    private List<MyClassDTO> mapEntityToDTO(List<MyClass> myClasses) {

        List<MyClassDTO> myClassList = new ArrayList<>();
        for (MyClass myClass : myClasses) {
            myClassList.add(mapEntityToDTO(myClass));
        }
        return myClassList;
    }

    // I guess this would be the definition of gambiarra, but works as intended in every way
    private <T> List<T> mapEntityToDTO(List<?> list, Class<T> dtoClass) {
        List<T> dtoList = new ArrayList<>();
        try {
            dtoList.add(createDefaultDTO(dtoClass));
            for (Object item : list) {
                dtoList.add(createDTOFromEntity(item, dtoClass));
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorMessage("Falha ao mapear DTOs!");
        }
        return dtoList;
    }

    private <T> T createDefaultDTO(Class<T> dtoClass) throws Exception {
        T dto = dtoClass.getDeclaredConstructor().newInstance();
        String displayName = getDisplayName(dtoClass);
        dtoClass.getMethod("setId", String.class).invoke(dto, "0");
        dtoClass.getMethod("setName", String.class).invoke(dto, displayName);
        return dto;
    }

    private String getDisplayName(Class<?> dtoClass) {
        if (dtoClass.equals(ProfessorDTO.class)) {
            return "Escolha um professor";
        } else if (dtoClass.equals(DisciplineDTO.class)) {
            return "Escolha uma disciplina";
        } else {
            return "Escolha uma sala";
        }
    }

    private <T> T createDTOFromEntity(Object obj, Class<T> dtoClass) throws Exception {
        T dto = dtoClass.getDeclaredConstructor().newInstance();
        for (var field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            if (fieldName.equals("inactive")) {
                break;
            }

            Object value = field.get(obj);
            dtoClass.getMethod("set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1), String.class)
                    .invoke(dto, value != null ? value.toString() : null);
        }
        return dto;
    }
}

