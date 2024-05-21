package ba.unsa.etf.ppis.Service;

import ba.unsa.etf.ppis.Model.Lecture;
import ba.unsa.etf.ppis.Model.*;
import ba.unsa.etf.ppis.Model.UsersLecture;
import ba.unsa.etf.ppis.Repository.*;
import ba.unsa.etf.ppis.Repository.UsersLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersLectureService {

    @Autowired
    private UsersLectureRepository usersLectureRepository;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private LectureRepository lectureRepo;

    public UsersLecture createUserLectureAssociation(String userId, String lectureId) {
        UsersLecture userLecture = new UsersLecture();
        User u= userRepo.findById(userId).orElse(null);
        Lecture l= lectureRepo.findById(lectureId).orElse(null);
        userLecture.setUser(u);
        userLecture.setLecture(l);
        // Set other properties as needed
        return usersLectureRepository.save(userLecture);
    }

    public List<Lecture> getUsersLectures(String userId) {
        User u= userRepo.findById(userId).orElse(null);
        List<UsersLecture> usersLectures= usersLectureRepository.findByUser(u);
        List<Lecture>l= new ArrayList<Lecture>();
        for(UsersLecture u1: usersLectures){
            l.add(lectureRepo.findById(u1.getLecture().getLectureId()).orElse(null));
        }
        return l;
    }

    // Get all user's lecture associations for a given lecture
    public List<UsersLecture> getLectureUserAssociations(String lectureId) {
        Lecture l=lectureRepo.findById(lectureId).orElse(null);
        return usersLectureRepository.findByLecture(l);
    }

    // Update a user's lecture association
    public UsersLecture updateUserLectureAssociation(String usersLectureId, UsersLecture updatedUserLecture) {
        Optional<UsersLecture> userLectureOptional = usersLectureRepository.findById(usersLectureId);
        if (userLectureOptional.isPresent()) {
            UsersLecture userLecture = userLectureOptional.get();
            // Update properties of userLecture with those from updatedUserLecture
            // For example: userLecture.setSomeProperty(updatedUserLecture.getSomeProperty());
            return usersLectureRepository.save(userLecture);
        } else {
            // Handle case where user's lecture association is not found
            return null;
        }
    }

    // Delete a user's lecture association
    public void deleteUserLectureAssociation(String usersLectureId) {
        usersLectureRepository.deleteById(usersLectureId);
    }
}
