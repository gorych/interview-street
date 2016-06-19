package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.EmployeeDAO;
import by.gstu.interviewstreet.dao.InterviewDAO;
import by.gstu.interviewstreet.dao.SubdivisionDAO;
import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * Сервис для выполнения операций с анкетами
 */
@Service
public class InterviewServiceImpl implements InterviewService {

    /**
     * DAO для работы с сущностью "Employee"
     */
    @Autowired
    private EmployeeDAO employeeDAO;

    /**
     * DAO для работы с сущностью "Interview"
     */
    @Autowired
    private InterviewDAO interviewDAO;

    /**
     * DAO для работы с сущностью "Subdivision"
     */
    @Autowired
    private SubdivisionDAO subdivisionDAO;

    /**
     * DAO для работы с сущностью "UserInterview"
     */
    @Autowired
    private UserInterviewDAO userInterviewDAO;

    /**
     * Получает все анкеты из БД
     *
     * @return список всех анкет
     */
    @Override
    @Transactional(readOnly = true)
    public List<Interview> getAll() {
        return interviewDAO.getAll();
    }

    /**
     * Получает список всех публикаций по анкете
     *
     * @param interview анкета
     * @return список все публикаций
     */
    @Override
    @Transactional(readOnly = true)
    public List<PublishedInterview> getPublishedInterviews(Interview interview) {
        return interviewDAO.getPublishedInterviews(interview);
    }

    /**
     * Строит карту для "формы" редактирования анкеты
     *
     * @param interviewId идентификатор анкеты
     * @return построенная карта
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getModelMapForEditForm(int interviewId) {
        Interview interview = interviewDAO.find(interviewId);
        List<UserInterview> userInterviews = userInterviewDAO.getByInterviewAndGroupByPost(interviewId);

        List<Post> activePosts = new ArrayList<>();
        List<Integer> activeSubIds = new ArrayList<>();

        for (UserInterview userInterview : userInterviews) {
            activePosts.add(userInterview.getUser().getEmployee().getPost());
            activeSubIds.add(userInterview.getUser().getEmployee().getSubdivision().getId());
        }

        Map<Object, String> posts = new HashMap<>();
        Map<Object, String> subdivisions = new TreeMap<>();
        if (activeSubIds.size() > 0) {

            /*Список сотрудник с заданной должностью*/
            List<Employee> employees = employeeDAO.getBySubdivisionIds(activeSubIds);

            for (Employee employee : employees) {
                Post post = employee.getPost();

                posts.put(post, activePosts.contains(post) ? "selected" : "not_selected");
            }

            List<Subdivision> subs = subdivisionDAO.getAll();

            for (Subdivision sub : subs) {
                subdivisions.put(sub, activeSubIds.contains(sub.getId()) ? "selected" : "not_selected");
            }

        }

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put(AttrConstants.POSTS, posts);
        valueMap.put(AttrConstants.SUBDIVISIONS, subdivisions);
        valueMap.put(AttrConstants.INTERVIEW, interview);

        return valueMap;
    }

    /**
     * Получает анкету по идентификатору
     *
     * @param interviewId идентификатор анкеты
     * @return анкета
     */
    @Override
    @Transactional(readOnly = true)
    public Interview get(int interviewId) {
        return interviewDAO.find(interviewId);
    }

    /**
     * Получает анкету по хещ-коду
     *
     * @param hash хещ-код
     * @return анкета
     */
    @Override
    @Transactional(readOnly = true)
    public Interview get(String hash) {
        return interviewDAO.getByHash(hash);
    }

    /**
     * Получает публикацию анкеты по идентификатору
     *
     * @param id идентификатор
     * @return публикация
     */
    @Override
    @Transactional(readOnly = true)
    public PublishedInterview getPublish(Integer id) {
        return interviewDAO.getPublishedById(id);
    }

    /**
     * Сохраняет или обновляет анкету
     *
     * @param interview анкета для обновления
     * @return обновленная анкета
     */
    @Override
    @Transactional
    public Interview saveOrUpdate(Interview interview) {
        Interview existed = interviewDAO.find(interview.getId());

        /*create new interview*/
        if (existed == null) {
            byte[] bytes = (interview.getName() + System.currentTimeMillis()).getBytes();
            interview.setHash(DigestUtils.md5DigestAsHex(bytes));
            interviewDAO.saveOrUpdate(interview);

            return interview;
        }

        removeAllUserInterviews(existed);

        existed.setName(interview.getName());
        existed.setType(interview.getType());
        existed.setGoal(interview.getGoal());
        existed.setEndDate(interview.getEndDate());
        existed.setAudience(interview.getAudience());
        existed.setPlacementDate(DateUtils.getToday());
        existed.setDescription(interview.getDescription());
        existed.setSecondPassage(interview.isSecondPassage());

        existed.setHide(true);

        interviewDAO.saveOrUpdate(existed);
        return existed;
    }

    /**
     * Сохраняет экспертную анкету
     *
     * @param expertInterview анкета для сохранения
     */
    @Override
    @Transactional
    public void saveExpertInterview(ExpertInterview expertInterview) {
        interviewDAO.saveExpertInterview(expertInterview);
    }

    /**
     * Обнавляет анкету
     *
     * @param interview анкета для обновления
     */
    @Override
    @Transactional
    public void update(Interview interview) {
        interviewDAO.saveOrUpdate(interview);
    }

    /**
     * Удаляет все записи пользователя связанные с заданной анкетой
     *
     * @param interview анкета для удаления
     */
    private void removeAllUserInterviews(Interview interview) {
        List<UserInterview> userInterviews = userInterviewDAO.getByInterviewAndGroupByPost(interview.getId());
        for (UserInterview ui : userInterviews) {
            userInterviewDAO.remove(ui);
        }
    }

    /**
     * Удаляет анкету
     *
     * @param interview анкета
     */
    @Override
    @Transactional
    public void remove(Interview interview) {
        interviewDAO.remove(interview);
    }

    /**
     * Блокирует и разблокирует анкету по идентификатору
     *
     * @param id идентификатор
     */
    @Override
    @Transactional
    public void lockOrUnlock(int id) {
        interviewDAO.lockOrUnlock(id);
    }
}
