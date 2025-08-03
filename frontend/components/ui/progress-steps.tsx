interface ProgressStepsProps {
  currentStep: number;
  totalSteps: number;
}

export function ProgressSteps({ currentStep, totalSteps }: ProgressStepsProps) {
  return (
    <div>
      <div className="flex items-center justify-center mb-12">
        <div className="flex items-center w-full max-w-xl px-4">
          {Array.from({ length: totalSteps }, (_, i) => i+1).map((step, index) => (
            <div key={step} className={`flex items-center relative ${index === totalSteps-1 ? '':'w-full'} `}>
              {/* Circle */}
              <div className="flex flex-col items-center z-10">
                <div
                  className={`w-12 h-12 rounded-full flex items-center justify-center text-white font-bold 
                    ${currentStep === step ? "bg-black" : "bg-gray-300"}`}
                >
                  {step}
                </div>
                <span className="mt-2 text-sm font-medium">Step {step}</span>
              </div>

              {/* Line */}
              {index < totalSteps - 1 && (
                <div className="absolute top-6 w-full">
                  <div className="h-px bg-gray-300 w-full translate-x-6" />
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
