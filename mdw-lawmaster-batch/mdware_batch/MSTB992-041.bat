@echo off
rem =======================================
rem MSTB992-041 POS�p���i�}�X�^�G���[���[�N�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-041 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
