package com.example.findcare.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.findcare.database.AppDatabase;
import com.example.findcare.models.Doctor;
import com.example.findcare.R;

import java.util.ArrayList;
import java.util.List;

public class DataSeeder {

        private static final String PREF_NAME = "FindCarePrefs";
        private static final String IS_SEEDED = "IsSeeded";

        public static void seedDoctors(Context context) {
                AppDatabase db = AppDatabase.getDatabase(context);

                // Always clear old data to ensure fresh seed matches requirements
                db.doctorDao().deleteAll();

                if (db.doctorDao().getAllDoctors().isEmpty()) {
                        List<Doctor> doctors = new ArrayList<>();
                        int img = com.example.findcare.R.drawable.doctor;

                        // --- Dentist (5) ---
                        doctors.add(new Doctor("Dr. Sarah Smith", "Dentist", "Smile Studio", 4.8f, 12, 120, img,
                                        "Dr. Sarah Smith is a cosmetic dentist known for creating perfect smiles. She specializes in veneers and teeth whitening."));
                        doctors.add(new Doctor("Dr. John Doe", "Dentist", "City Dental", 4.5f, 8, 100, img,
                                        "Dr. John Doe provides comprehensive dental care for families. He is great with kids and anxious patients."));
                        doctors.add(new Doctor("Dr. Emily White", "Dentist", "Bright Dental", 4.9f, 15, 150, img,
                                        "Dr. Emily White is an expert in restorative dentistry and implants. She uses the latest technology for painless treatments."));
                        doctors.add(new Doctor("Dr. Mark Green", "Dentist", "Green Leaf Dental", 4.6f, 10, 110, img,
                                        "Dr. Mark Green focuses on preventive care and gum health. He believes in educating patients for long-term oral hygiene."));
                        doctors.add(new Doctor("Dr. Lisa Brown", "Dentist", "Premium Smiles", 4.7f, 11, 130, img,
                                        "Dr. Lisa Brown specializes in orthodontics and aligners. She helps patients achieve straight, confident smiles."));

                        // --- Cardiologist (5) ---
                        doctors.add(new Doctor("Dr. Emily Davis", "Cardiologist", "Heart Center", 4.9f, 15, 200, img,
                                        "Dr. Emily Davis is a leading cardiologist specializing in preventive heart care and lifestyle management."));
                        doctors.add(new Doctor("Dr. Robert Wilson", "Cardiologist", "City Hospital", 4.8f, 20, 250, img,
                                        "Dr. Robert Wilson is a renowned heart surgeon with expertise in complex bypass surgeries and valve repairs."));
                        doctors.add(new Doctor("Dr. Jennifer Lee", "Cardiologist", "Heart & vascular", 4.7f, 12, 180,
                                        img,
                                        "Dr. Jennifer Lee focuses on women's heart health and non-invasive diagnostic techniques."));
                        doctors.add(new Doctor("Dr. David Miller", "Cardiologist", "Miller Cardiology", 4.6f, 18, 220,
                                        img,
                                        "Dr. David Miller has extensive experience in treating arrhythmias and implementing pacemakers."));
                        doctors.add(new Doctor("Dr. Susan Clark", "Cardiologist", "Community Health", 4.5f, 10, 150,
                                        img,
                                        "Dr. Susan Clark provides accessible heart care with a focus on hypertension management and rehabilitation."));

                        // --- Pulmonologist (Lungs) (5) ---
                        doctors.add(new Doctor("Dr. Alan Grant", "Pulmonologist", "Lung Institute", 4.7f, 14, 160, img,
                                        "Dr. Alan Grant specializes in asthma and COPD management. He is dedicated to helping patients breathe easier."));
                        doctors.add(new Doctor("Dr. Laura Dern", "Pulmonologist", "Breath Easy Clinic", 4.8f, 16, 175,
                                        img,
                                        "Dr. Laura Dern follows a holistic approach to treating respiratory infections and allergies."));
                        doctors.add(new Doctor("Dr. Ian Malcolm", "Pulmonologist", "Central Chest Care", 4.5f, 9, 140,
                                        img,
                                        "Dr. Ian Malcolm focuses on sleep apnea and sleep-related breathing disorders."));
                        doctors.add(new Doctor("Dr. Ellie Sattler", "Pulmonologist", "Paleo Health", 4.9f, 11, 190, img,
                                        "Dr. Ellie Sattler is an expert in pulmonary fibrosis and interstitial lung diseases."));
                        doctors.add(new Doctor("Dr. John Hammond", "Pulmonologist", "Hammond Respiratory", 4.6f, 25,
                                        300, img,
                                        "Dr. John Hammond is a veteran specialist in critical care pulmonology and advanced therapeutics."));

                        // --- Neurologist (Brain) (5) ---
                        doctors.add(new Doctor("Dr. Michael Brown", "Neurologist", "Brain Health", 4.7f, 18, 250, img,
                                        "Dr. Michael Brown treats simple to complex neurological disorders including migraines and epilepsy."));
                        doctors.add(new Doctor("Dr. Sarah Connor", "Neurologist", "Neural Pathways", 4.8f, 13, 220, img,
                                        "Dr. Sarah Connor specializes in neurodegenerative diseases like Alzheimer's and Parkinson's."));
                        doctors.add(new Doctor("Dr. Kyle Reese", "Neurologist", "Future Minds", 4.6f, 10, 200, img,
                                        "Dr. Kyle Reese focuses on stroke rehabilitation and brain injury recovery."));
                        doctors.add(new Doctor("Dr. T-800", "Neurologist", "Cyberdyne Systems", 5.0f, 30, 500, img,
                                        "Dr. T-800 is an expert in neuro-robotics and advanced brain-computer interfaces. High precision."));
                        doctors.add(new Doctor("Dr. John Connor", "Neurologist", "Resistance Care", 4.5f, 7, 180, img,
                                        "Dr. John Connor specializes in pediatric neurology and developmental disorders."));

                        // --- Otology (Ear) (5) ---
                        doctors.add(new Doctor("Dr. Vincent Van Gogh", "Otology", "Starry Hear", 4.2f, 20, 150, img,
                                        "Dr. Vincent specializes in hearing loss and audiology assessments."));
                        doctors.add(new Doctor("Dr. Ludwig Beethoven", "Otology", "Symphony Clinic", 4.9f, 25, 200, img,
                                        "Dr. Ludwig is an expert in treating tinnitus and complex inner ear disorders."));
                        doctors.add(new Doctor("Dr. Adele Sky", "Otology", "Sound Health", 4.7f, 8, 120, img,
                                        "Dr. Adele Sky focuses on ear infections and balance disorders in children and adults."));
                        doctors.add(new Doctor("Dr. Bruno Mars", "Otology", "Uptown Clinic", 4.6f, 12, 140, img,
                                        "Dr. Bruno Mars specializes in reconstructive ear surgery and cochlear implants."));
                        doctors.add(new Doctor("Dr. Sia Furler", "Otology", "Chandelier Ears", 4.8f, 15, 170, img,
                                        "Dr. Sia Furler offers comprehensive care for vertigo and vestibular disorders."));

                        // --- Opthalmologist (Eye) (5) ---
                        doctors.add(new Doctor("Dr. Hawkeye Pierce", "Opthalmologist", "Vision 20/20", 4.8f, 15, 160,
                                        img,
                                        "Dr. Hawkeye provides routine eye exams and prescribes glasses and contact lenses."));
                        doctors.add(new Doctor("Dr. Stephen Strange", "Opthalmologist", "Sanctum Vision", 5.0f, 10, 250,
                                        img,
                                        "Dr. Strange specializes in neuro-ophthalmology and complex vision problems."));
                        doctors.add(new Doctor("Dr. Cyclops Summers", "Opthalmologist", "X-Vision", 4.5f, 12, 140, img,
                                        "Dr. Cyclops is an expert in laser eye surgery (LASIK) and corrective procedures."));
                        doctors.add(new Doctor("Dr. Mad Eye Moody", "Opthalmologist", "Constant Vigilance", 4.4f, 30,
                                        200, img,
                                        "Dr. Moody treats varying eye injuries and prosthetic eye management."));
                        doctors.add(new Doctor("Dr. Iris West", "Opthalmologist", "Flash Focus", 4.7f, 9, 130, img,
                                        "Dr. Iris West focuses on diabetic retinopathy and age-related macular degeneration."));

                        // --- Dermatologist (Skin) (5) ---
                        doctors.add(new Doctor("Dr. Sandra Lee", "Dermatologist", "Pimple Popper Clinic", 4.9f, 15, 200,
                                        img,
                                        "Dr. Sandra Lee is a world-renowned dermatologist known for treating cysts, lipomas, and acne."));
                        doctors.add(new Doctor("Dr. Hannibal Lecter", "Dermatologist", "Skin Deep", 4.5f, 20, 250, img,
                                        "Dr. Lecter has a unique approach to skin grafting and reconstructive surgery. Very precise."));
                        doctors.add(new Doctor("Dr. Zoidberg", "Dermatologist", "Decapod Derm", 4.0f, 5, 80, img,
                                        "Dr. Zoidberg specializes in treating scales, shells, and other exoskeleton issues."));
                        doctors.add(new Doctor("Dr. Poison Ivy", "Dermatologist", "Botanical Skin", 4.7f, 10, 150, img,
                                        "Dr. Ivy uses organic, plant-based treatments for rashes and allergic reactions."));
                        doctors.add(new Doctor("Dr. Mystique", "Dermatologist", "Shapeshift Clinic", 4.8f, 12, 180, img,
                                        "Dr. Mystique specializes in scar removal and complete skin transformation procedures."));

                        // --- Pediatrician (Kids) (5) ---
                        doctors.add(new Doctor("Dr. Doug Ross", "Pediatrician", "County General", 4.8f, 10, 150, img,
                                        "Dr. Doug Ross is a dedicated pediatrician who goes above and beyond for his young patients."));
                        doctors.add(new Doctor("Dr. Doogie Howser", "Pediatrician", "Teen Health", 4.9f, 5, 120, img,
                                        "Dr. Doogie is a prodigy in pediatric medicine, relating exceptionally well to children and teens."));
                        doctors.add(new Doctor("Dr. Patch Adams", "Pediatrician", "Laughter Clinic", 5.0f, 20, 100, img,
                                        "Dr. Patch Adams believes laughter is the best medicine and brings joy to every check-up."));
                        doctors.add(new Doctor("Dr. Meredith Grey", "Pediatrician", "Grey Sloan", 4.7f, 15, 200, img,
                                        "Dr. Grey offers compassionate care for children with complex medical needs."));
                        doctors.add(new Doctor("Dr. Chris Turk", "Pediatrician", "Sacred Heart", 4.6f, 8, 140, img,
                                        "Dr. Turk provides energetic and fun care for kids, specializing in sports injuries."));

                        // --- Orthopedist (Bones) (5) ---
                        doctors.add(new Doctor("Dr. Bones McCoy", "Orthopedist", "Enterprise Health", 4.9f, 25, 250,
                                        img,
                                        "Dr. McCoy uses advanced technology to heal fractures and bone density issues instantly."));
                        doctors.add(new Doctor("Dr. Gregory House", "Orthopedist", "Diagnostics Dept", 4.5f, 20, 300,
                                        img,
                                        "Dr. House specializes in diagnosing rare bone diseases and persistent pain. Unconventional methods."));
                        doctors.add(new Doctor("Dr. Calliope Torres", "Orthopedist", "Ortho Excellence", 4.8f, 12, 220,
                                        img,
                                        "Dr. Torres is an expert in orthopedic surgery, reducing recovery time for athletes."));
                        doctors.add(new Doctor("Dr. Strange (Hands)", "Orthopedist", "Reconstructive Center", 4.7f, 15,
                                        280, img,
                                        "Former neurosurgeon now focusing on intricate hand and nerve reconstruction."));
                        doctors.add(new Doctor("Dr. Wolverine", "Orthopedist", "Adamantium Clinic", 5.0f, 50, 150, img,
                                        "Dr. Logan specializes in rapid bone healing and metallic implants. Rugged approach."));

                        // --- General Practitioner (General) (5) ---
                        doctors.add(new Doctor("Dr. John Watson", "General Practitioner", "Baker Street Surgery", 4.7f,
                                        10,
                                        100, img,
                                        "Dr. Watson offers reliable general care and is excellent at noticing details others miss."));
                        doctors.add(new Doctor("Dr. Michaela Quinn", "General Practitioner", "Frontier Medicine", 4.8f,
                                        15,
                                        90, img,
                                        "Dr. Quinn provides holistic family medicine and cares for patients of all ages."));
                        doctors.add(new Doctor("Dr. Leonard McCoy", "General Practitioner", "Starfleet Medical", 4.9f,
                                        20,
                                        120, img,
                                        "Dr. McCoy handles everything from common colds to exotic ailments with a grumpy but caring attitude."));
                        doctors.add(new Doctor("Dr. Perry Cox", "General Practitioner", "Sacred Heart", 4.6f, 18, 150,
                                        img,
                                        "Dr. Cox is a tough tutor but an brilliant diagnostician for internal medicine issues."));
                        doctors.add(new Doctor("Dr. Miranda Bailey", "General Practitioner", "Grey Sloan", 4.9f, 14,
                                        180,
                                        img,
                                        "Dr. Bailey runs a tight ship and ensures every patient receives top-tier general care."));

                        db.doctorDao().insertAll(doctors);
                }
        }
}
