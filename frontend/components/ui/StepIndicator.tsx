type Step = {
  number: number;
  label: string;
};

type Props = {
  currentStep: number;
  maxStepReached?: number;
  steps?: Step[];
  onStepChange?: (stepNumber: number) => void;
};

const defaultSteps: Step[] = [
  { number: 1, label: 'Step 1' },
  { number: 2, label: 'Step 2' },
  { number: 3, label: 'Step 3' },
];

export default function StepIndicator({
  currentStep,
  maxStepReached,
  steps = defaultSteps,
  onStepChange,
}: Props) {
  return (
    <div className='flex justify-center mt-10 sm:mt-16'>
      <div className='relative flex items-center w-full max-w-2xl'>
        {steps.map((step, index) => {
          const isActive = currentStep === step.number;
          const isPassed = currentStep > step.number;
          const isDisabled = step.number > (maxStepReached ?? currentStep); // ✅ logic disable

          return (
            <div
              key={step.number}
              className={`flex-1 flex flex-col items-center relative 
                ${isDisabled ? 'cursor-not-allowed opacity-50' : 'cursor-pointer group'}`}
              onClick={() => {
                if (!isDisabled) onStepChange?.(step.number);
              }}
            >
              {/* Line */}
              {index < steps.length - 1 && (
                <div className='absolute top-6 left-1/2 w-full h-[2px] z-0'>
                  <div
                    className={`h-full ${isPassed ? 'bg-black' : 'bg-[#d0d0d0]'}`}
                  ></div>
                </div>
              )}

              {/* Circle */}
              <div
                className={`relative z-10 w-12 h-12 sm:w-[62px] sm:h-[62px] rounded-full flex items-center justify-center
                    ${
                      isActive
                        ? 'bg-black text-white'
                        : isPassed
                          ? 'bg-gray-300 text-black border border-[#888]'
                          : 'bg-[#ebebeb] text-black border border-[#434343]'
                    } ${!isDisabled ? 'group-hover:ring-2 group-hover:ring-black' : ''}`}
              >
                <span className='text-lg sm:text-2xl'>{step.number}</span>
              </div>

              {/* Label */}
              <span
                className={`font-semibold text-sm sm:text-xl mt-2 sm:mt-[10px] 
                    ${isActive ? 'text-black' : 'text-gray-500'}`}
              >
                {step.label}
              </span>
            </div>
          );
        })}
      </div>
    </div>
  );
}
