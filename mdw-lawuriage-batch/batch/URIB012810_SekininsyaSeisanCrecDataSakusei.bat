REM @echo off
REM ============================================================================
REM �^�C�g��	�F���Z�f�[�^�쐬�����i�ӔC�Ґ��ZCrec�j
REM ����	�F���Z�f�[�^�쐬�����i�ӔC�Ґ��ZCrec�j�����s����B
REM version 1.00 2016/11/14 J.Endo:�V�K
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== ���Z�f�[�^�쐬�����i�ӔC�Ґ��ZCrec�j
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012810
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
