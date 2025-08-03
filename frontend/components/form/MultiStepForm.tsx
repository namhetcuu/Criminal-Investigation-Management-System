// MultiStepForm.tsx
import { useState } from 'react';
import Step1 from './Step1';
import Step2 from './Step2';
import Step3 from './Step3';
import StepIndicator from '../ui/StepIndicator';
import { useEffect } from 'react';
export default function MultiStepForm() {
  const [step, setStep] = useState(1);
  const [maxStepReached, setMaxStepReached] = useState(1);
  const [formData, setFormData] = useState({
    // Step1
    fullName: '',
    email: '',
    phone: '',
    address: '',
    relationship: '',

    // Step2
    typeOfCrime: '',
    severity: '',
    incidentDate: '',
    incidentAddress: '',
    incidentDescription: '',
    relevantParties:
      typeof window !== 'undefined'
        ? JSON.parse(sessionStorage.getItem('relevantParties') || '[]')
        : [],
    initialEvidence:
      typeof window !== 'undefined'
        ? JSON.parse(sessionStorage.getItem('initialEvidence') || '[]')
        : [],
  });

  const handleNext = (data: any) => {
    setFormData((prev) => ({ ...prev, ...data }));
    setStep((prev) => {
      const nextStep = prev + 1;
      if (nextStep > maxStepReached) setMaxStepReached(nextStep);
      return nextStep;
    });
  };

  const handleBack = () => setStep((prev) => prev - 1);

  const handleSubmit = () => {
    console.log('Submitted:', formData);
  };
  const updateFormData = (data: any) => {
    setFormData((prev) => ({ ...prev, ...data }));
  };
  const nextStep = () => {
    setStep((prev) => {
      const next = prev + 1;
      if (next > maxStepReached) setMaxStepReached(next);
      return next;
    });
  };
  const prevStep = () => {
    setStep((prev) => prev - 1);
  };

  useEffect(() => {
    const resume = sessionStorage.getItem('resumeStep');
    if (resume) {
      const resumeStep = Number(resume);
      setStep(resumeStep);
      setMaxStepReached(resumeStep);
      sessionStorage.removeItem('resumeStep');
    }
  }, []);

  const handleStepChange = (n: number) => {
    if (n <= maxStepReached) {
      setStep(n);
    }
  };

  return (
    <div className='max-w-3xl mx-auto px-4 py-10'>
      <StepIndicator
        currentStep={step}
        maxStepReached={maxStepReached}
        onStepChange={handleStepChange}
      />
      <div className='mt-10'>
        {step === 1 && <Step1 data={formData} onNext={handleNext} />}
        {step === 2 && (
          // <div>
          //   <h1>Step 2</h1>
          // </div>
          <Step2
            data={formData}
            onBack={handleBack}
            onNext={nextStep} // chỉ điều khiển chuyển bước
            onUpdate={updateFormData}
          />
        )}
        {step === 3 && (
          <Step3 data={formData} onBack={handleBack} onSubmit={handleSubmit} />
        )}
      </div>
    </div>
  );
}
